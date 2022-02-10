package com.example.gitapitestapp.domain

import androidx.lifecycle.MutableLiveData
import com.example.gitapitestapp.domain.models.ComItem
import com.example.gitapitestapp.domain.models.RepositoriesItem
import retrofit2.Response

interface Repository {
    suspend fun getRepositories(number: Int): Response<ArrayList<RepositoriesItem>>
    suspend fun getCommits(owner: String, repo: String): Response<ArrayList<ComItem>>
    fun returnRepositories(): MutableLiveData<ArrayList<RepositoriesItem>>
}