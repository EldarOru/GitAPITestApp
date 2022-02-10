package com.example.gitapitestapp.domain.usecases

import com.example.gitapitestapp.domain.Repository
import com.example.gitapitestapp.domain.models.ComItem
import retrofit2.Response

class GetCommitsUseCase(private val repository: Repository) {
    suspend fun getCommits(owner: String, repo: String): Response<ArrayList<ComItem>>{
        return repository.getCommits(owner, repo)
    }
}