package com.example.gitapitestapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitapitestapp.domain.Repository
import com.example.gitapitestapp.domain.usecases.FetchRepositoriesLiveDataUseCase
import com.example.gitapitestapp.domain.usecases.GetCommitsUseCase
import com.example.gitapitestapp.domain.usecases.GetRepositoriesUseCase
import com.example.gitapitestapp.domain.usecases.ReturnRepositoriesUseCase

class ViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {

    private val getRepositoriesUseCase = GetRepositoriesUseCase(repository)
    private val returnRepositoriesUseCase = ReturnRepositoriesUseCase(repository)
    private val getCommitsUseCase = GetCommitsUseCase(repository)
    private val fetchRepositoriesLiveDataUseCase = FetchRepositoriesLiveDataUseCase(repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)){
            return MainFragmentViewModel(getRepositoriesUseCase = getRepositoriesUseCase,
                returnRepositoriesUseCase = returnRepositoriesUseCase,
                fetchRepositoriesLiveDataUseCase = fetchRepositoriesLiveDataUseCase) as T
        }
        if (modelClass.isAssignableFrom(DetailedFragmentViewModel::class.java)){
            return DetailedFragmentViewModel(getCommitsUseCase = getCommitsUseCase) as T
        }
        throw IllegalAccessException("ViewModel class is not found")
    }
}