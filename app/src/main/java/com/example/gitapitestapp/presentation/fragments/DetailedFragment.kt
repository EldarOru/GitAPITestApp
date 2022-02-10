package com.example.gitapitestapp.presentation.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.gitapitestapp.data.RepositoryImpl
import com.example.gitapitestapp.databinding.DetailedFragmentBinding
import com.example.gitapitestapp.presentation.adapters.RepositoriesListAdapter
import com.example.gitapitestapp.presentation.adapters.ShaListAdapter
import com.example.gitapitestapp.presentation.viewmodels.DetailedFragmentViewModel
import com.example.gitapitestapp.presentation.viewmodels.MainFragmentViewModel
import com.example.gitapitestapp.presentation.viewmodels.ViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DetailedFragment: Fragment() {

    private lateinit var detailedFragmentBinding: DetailedFragmentBinding
    private lateinit var detailedFragmentViewModel: DetailedFragmentViewModel
    private lateinit var shaListAdapter: ShaListAdapter
    private var avatarURL = ""
    private var login = ""
    private var fullName = ""
    private var name = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailedFragmentBinding = DetailedFragmentBinding.inflate(inflater, container, false)
        return detailedFragmentBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailedFragmentViewModel = ViewModelProvider(this, ViewModelFactory(RepositoryImpl)).get(
            DetailedFragmentViewModel::class.java)
        getArgs()
        setInfo()
        setRecyclerView()
        detailedFragmentViewModel.getCommits(login, name)

        detailedFragmentViewModel.commitsLiveData.observe(viewLifecycleOwner){
            shaListAdapter.list = it[0].parents
            detailedFragmentBinding.apply {
                val dateFormatDefault = SimpleDateFormat("yyyy-MM-dd")
                val newDateFormat = SimpleDateFormat("dd.MM.yyyy")
                val date: Date = dateFormatDefault.parse(it[0].commit.author.date.substring(0,10))
                commitAuthorDate.text = "Date: ${newDateFormat.format(date)}"
                commitAuthorName.text = "Author name: ${it[0].commit.author.name}"
                commitMessage.text = "Message: ${it[0].commit.message}"
            }
        }

        detailedFragmentViewModel.errorMessage.observe(viewLifecycleOwner){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getArgs(){
        avatarURL = requireArguments().getString(AVATAR_URL).toString()
        login = requireArguments().getString(LOGIN).toString()
        fullName = requireArguments().getString(FULL_NAME).toString()
        name = requireArguments().getString(NAME).toString()
    }

    private fun setInfo(){
        detailedFragmentBinding.apply {
            loginTv.text = "User login: $login"
            fullNameTv.text = "Full name: $fullName"
            Glide.with(requireContext()).load(avatarURL).into(avatarIv)
        }
    }

    private fun setRecyclerView(){
        val recyclerView = detailedFragmentBinding.shaRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        shaListAdapter = ShaListAdapter()
        recyclerView.adapter = shaListAdapter
    }

    companion object{
        fun newInstanceDetailedFragment(avatarURL: String,
                                        login: String,
                                        fullName: String,
                                        name: String): DetailedFragment{
            return DetailedFragment().apply {
                arguments = Bundle().apply {
                    putString(AVATAR_URL, avatarURL)
                    putString(LOGIN, login)
                    putString(FULL_NAME, fullName)
                    putString(NAME, name)
                }
            }
        }
        private const val AVATAR_URL = "AVATAR_URL"
        private const val LOGIN = "LOGIN"
        private const val FULL_NAME = "FULL_NAME"
        private const val NAME = "NAME"
    }
}