package com.example.gitapitestapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitapitestapp.data.RepositoryImpl
import com.example.gitapitestapp.databinding.MainFragmentBinding
import com.example.gitapitestapp.presentation.adapters.RepositoriesPagedAdapter
import com.example.gitapitestapp.presentation.adapters.LoadStateAdapter
import com.example.gitapitestapp.presentation.viewmodels.MainFragmentViewModel
import com.example.gitapitestapp.presentation.viewmodels.ViewModelFactory
import kotlinx.coroutines.launch
import java.lang.RuntimeException

class MainFragment: Fragment() {
    private lateinit var mainFragmentBinding: MainFragmentBinding
    private lateinit var repositoriesListAdapter: RepositoriesPagedAdapter
    private lateinit var mainFragmentViewModel: MainFragmentViewModel
    private lateinit var onFragmentInteractionsListener: OnFragmentInteractionsListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionsListener) {
            onFragmentInteractionsListener = context
        } else {
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
        mainFragmentViewModel = ViewModelProvider(
            this,
            ViewModelFactory(RepositoryImpl)
        ).get(MainFragmentViewModel::class.java)
        setRecyclerView()
        setOnClick()
        fetchDoggoImagesLiveData()
        setLoadStateListener()

        mainFragmentBinding.btnRetry.setOnClickListener {
            repositoriesListAdapter.retry()
        }
    }

    private fun setRecyclerView() {
        val recyclerView = mainFragmentBinding.repositoriesRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        repositoriesListAdapter = RepositoriesPagedAdapter(requireContext())
        recyclerView.adapter = repositoriesListAdapter.withLoadStateFooter(
            footer = LoadStateAdapter{repositoriesListAdapter.retry()}
        )
    }

    private fun fetchDoggoImagesLiveData() {
        mainFragmentViewModel.fetchDoggoImagesLiveData().observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                repositoriesListAdapter.submitData(it)
            }
        })
    }

    private fun setOnClick() {
        repositoriesListAdapter.onRepositoryClickListener = {
            onFragmentInteractionsListener.onAddBackStack(
                "check",
                DetailedFragment.newInstanceDetailedFragment(
                    avatarURL = it.owner.avatar_url,
                    login = it.owner.login,
                    fullName = it.full_name,
                    name = it.name
                )
            )
        }
    }

    private fun setLoadStateListener(){
        repositoriesListAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading){
                mainFragmentBinding.btnRetry.visibility = View.GONE

                mainFragmentBinding.progressBar.visibility = View.VISIBLE
            }
            else {
                mainFragmentBinding.progressBar.visibility = View.GONE

                val errorState = when {
                    it.append is LoadState.Error -> it.append as LoadState.Error
                    it.prepend is LoadState.Error -> it.prepend as LoadState.Error
                    it.refresh is LoadState.Error -> {
                        mainFragmentBinding.btnRetry.visibility = View.VISIBLE
                        it.refresh as LoadState.Error
                    }
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
