package com.example.gitapitestapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.gitapitestapp.data.RepositoryImpl
import com.example.gitapitestapp.domain.models.RepositoriesItem
import com.example.gitapitestapp.domain.usecases.GetRepositoriesUseCase
import com.example.gitapitestapp.domain.usecases.ReturnRepositoriesUseCase
import com.example.gitapitestapp.presentation.adapters.RepositoriesDataSource
import kotlinx.coroutines.*
import retrofit2.Response

class MainFragmentViewModel(private val getRepositoriesUseCase: GetRepositoriesUseCase,
                            private val returnRepositoriesUseCase: ReturnRepositoriesUseCase): ViewModel(){
    val onSuccess = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val repositoriesLiveData = returnRepositoriesUseCase.returnRepositories()
    private var job: Job? = null



    val passengers =
        Pager(config = PagingConfig(pageSize = 10), pagingSourceFactory = {
            RepositoriesDataSource(RepositoryImpl.retrofitServices)
        }).liveData.cachedIn(viewModelScope)



    fun getRepositories(number: Int){
        job = CoroutineScope(Dispatchers.IO).launch {
            val response: Response<ArrayList<RepositoriesItem>> = try {
                getRepositoriesUseCase.getRepositories(number)
            }catch (e: Exception){
                withContext(Dispatchers.Main) {
                    onSuccess.value = false
                    onError("Error : ${e.message} ")

                }
                return@launch
            }
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.d("ROCK", response.headers()["link"] ?: "no")
                    repositoriesLiveData.postValue(response.body())
                    onSuccess.value = true
                } else {
                    onError("Error : ${response.message()}")
                    //onSuccess.value = false
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}