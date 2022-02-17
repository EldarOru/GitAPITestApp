package com.example.gitapitestapp.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gitapitestapp.domain.Repository
import com.example.gitapitestapp.domain.models.RepositoriesItem

class FetchRepositoriesLiveDataUseCase(private val repository: Repository) {
    fun fetchRepositoriesLiveData(): LiveData<PagingData<RepositoriesItem>>{
        return repository.fetchRepositoriesLiveData()
    }
}