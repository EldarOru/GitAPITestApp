package com.example.gitapitestapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapitestapp.data.RepositoryImpl
import com.example.gitapitestapp.databinding.MainFragmentBinding
import com.example.gitapitestapp.domain.internet.RetrofitServices
import com.example.gitapitestapp.presentation.adapters.RepositoriesListAdapter
import com.example.gitapitestapp.presentation.viewmodels.MainFragmentViewModel
import com.example.gitapitestapp.presentation.viewmodels.ViewModelFactory
import java.lang.RuntimeException

class MainFragment: Fragment() {
    private lateinit var mainFragmentBinding: MainFragmentBinding
    private lateinit var repositoriesListAdapter: RepositoriesListAdapter
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private lateinit var onFragmentInteractionsListener: OnFragmentInteractionsListener
    private var page = 1
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionsListener){
            onFragmentInteractionsListener = context
        }else{
            throw RuntimeException("Activity must implement OnFragmentsInteractionsListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentBinding = MainFragmentBinding.inflate(inflater, container, false)
        return mainFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentViewModel = ViewModelProvider(this, ViewModelFactory(RepositoryImpl)).get(MainFragmentViewModel::class.java)
        setRecyclerView()
        setOnClick()
        mainFragmentViewModel.getRepositories(RetrofitServices.MAGIC_NUMBER)

        mainFragmentViewModel.repositoriesLiveData.observe(viewLifecycleOwner){
            repositoriesListAdapter.list = it.subList(page * 10 - 10, page * 10)
        }

        mainFragmentViewModel.errorMessage.observe(viewLifecycleOwner){
            if (mainFragmentViewModel.onSuccess.value == false) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setRecyclerView(){
        val recyclerView = mainFragmentBinding.repositoriesRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        repositoriesListAdapter = RepositoriesListAdapter(requireContext())
        recyclerView.adapter = repositoriesListAdapter
    }

    private fun setOnClick(){
        repositoriesListAdapter.onRepositoryClickListener = {
            onFragmentInteractionsListener.onAddBackStack("check",
            DetailedFragment.newInstanceDetailedFragment(avatarURL = it.owner.avatar_url,
                                                        login = it.owner.login,
                                                        fullName = it.full_name,
                                                        name = it.name))}

        //TODO REWORK PAGE
        mainFragmentBinding.nextButton.setOnClickListener {
            page++
            if (page * 10 > mainFragmentViewModel.repositoriesLiveData.value?.size ?: 21) {
                page--
                Toast.makeText(requireContext(), "End", Toast.LENGTH_SHORT).show()
            }else if (mainFragmentViewModel.onSuccess.value == false || mainFragmentViewModel.repositoriesLiveData.value == null){
                page--
                Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
            }else{
                repositoriesListAdapter.list =
                    mainFragmentViewModel.repositoriesLiveData.value?.subList(
                        page * 10 - 10,
                        page * 10) ?: arrayListOf()
            }
        }

        mainFragmentBinding.previousButton.setOnClickListener {
            page--
            if (page < 1){
                page++
                Toast.makeText(requireContext(), "First page", Toast.LENGTH_SHORT).show()
            }else if (mainFragmentViewModel.onSuccess.value == false || mainFragmentViewModel.repositoriesLiveData.value == null){
                page++
                Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
            }
            else{
                repositoriesListAdapter.list =
                    mainFragmentViewModel.repositoriesLiveData.value?.subList(
                        page * 10 - 10,
                        page * 10
                    ) ?: arrayListOf()
            }
        }

        mainFragmentBinding.reloadButton.setOnClickListener {
            mainFragmentViewModel.getRepositories(RetrofitServices.MAGIC_NUMBER)
        }
    }
}