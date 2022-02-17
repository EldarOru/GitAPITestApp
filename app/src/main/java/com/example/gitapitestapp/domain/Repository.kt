package com.example.gitapitestapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.gitapitestapp.data.RepositoryImpl
import com.example.gitapitestapp.domain.models.ComItem
import com.example.gitapitestapp.domain.models.RepositoriesItem
import retrofit2.Response

interface Repository {
    suspend fun getRepositories(number: Int): Response<ArrayList<RepositoriesItem>>
    suspend fun getCommits(owner: String, repo: String): Response<ArrayList<ComItem>>
    fun fetchRepositoriesLiveData(pagingConfig: PagingConfig = RepositoryImpl.getDefaultPageConfig()): LiveData<PagingData<RepositoriesItem>>
    fun returnRepositories(): MutableLiveData<ArrayList<RepositoriesItem>>
}