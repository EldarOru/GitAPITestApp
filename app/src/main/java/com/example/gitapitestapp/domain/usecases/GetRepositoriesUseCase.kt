package com.example.gitapitestapp.domain.usecases

import com.example.gitapitestapp.domain.Repository
import com.example.gitapitestapp.domain.models.RepositoriesItem
import retrofit2.Response

class GetRepositoriesUseCase(private val repository: Repository) {
    suspend fun getRepositories(number: Int): Response<ArrayList<RepositoriesItem>> {
        return repository.getRepositories(number)
    }
}