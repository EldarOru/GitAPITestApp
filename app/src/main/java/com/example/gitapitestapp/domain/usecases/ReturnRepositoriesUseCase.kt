package com.example.gitapitestapp.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gitapitestapp.domain.Repository
import com.example.gitapitestapp.domain.models.RepositoriesItem
import retrofit2.Response

class ReturnRepositoriesUseCase(private val repository: Repository) {
    fun returnRepositories(): MutableLiveData<ArrayList<RepositoriesItem>> {
        return repository.returnRepositories()
    }
}