package com.example.gitapitestapp.data

import androidx.lifecycle.MutableLiveData
import com.example.gitapitestapp.domain.Repository
import com.example.gitapitestapp.domain.internet.RetrofitServices
import com.example.gitapitestapp.domain.models.ComItem
import com.example.gitapitestapp.domain.models.RepositoriesItem
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

    /*
    fun getQuoteFromServer() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofitServices.getRepositories(265165)
            if (response.isSuccessful && response.body() != null) {
                newRep = response.body() as List<RepositoriesItem>
                Log.e("KEK", newRep.size.toString())
                withContext(Dispatchers.Main){
                }
            }
        }
    }

     */

}