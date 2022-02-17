package com.example.gitapitestapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.gitapitestapp.domain.Repository
import com.example.gitapitestapp.domain.internet.RetrofitServices
import com.example.gitapitestapp.domain.models.ComItem
import com.example.gitapitestapp.domain.models.RepositoriesItem
import com.example.gitapitestapp.presentation.adapters.RepositoriesDataSource
import retrofit2.Response

object RepositoryImpl: Repository {

    private val repositoriesListLiveData = MutableLiveData<ArrayList<RepositoriesItem>>()
    private val retrofitServices = RetrofitServices.getInstance()
    override suspend fun getRepositories(number: Int): Response<ArrayList<RepositoriesItem>> {
        return retrofitServices.getRepositories(number)
    }

    override suspend fun getCommits(owner: String, repo: String): Response<ArrayList<ComItem>> {
        return retrofitServices.getCommits(owner, repo)
    }

    override fun returnRepositories(): MutableLiveData<ArrayList<RepositoriesItem>> {
        return repositoriesListLiveData
    }

    override fun fetchRepositoriesLiveData(pagingConfig: PagingConfig): LiveData<PagingData<RepositoriesItem>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { RepositoriesDataSource(retrofitServices) }
        ).liveData
    }

    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = 20, enablePlaceholders = false)
    }

}