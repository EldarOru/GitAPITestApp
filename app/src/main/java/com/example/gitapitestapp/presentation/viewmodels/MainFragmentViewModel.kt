package com.example.gitapitestapp.presentation.viewmodels

import androidx.lifecycle.*
import androidx.paging.*
import com.example.gitapitestapp.domain.models.RepositoriesItem
import com.example.gitapitestapp.domain.usecases.FetchRepositoriesLiveDataUseCase
import com.example.gitapitestapp.domain.usecases.GetRepositoriesUseCase
import com.example.gitapitestapp.domain.usecases.ReturnRepositoriesUseCase
import kotlinx.coroutines.*

class MainFragmentViewModel(private val getRepositoriesUseCase: GetRepositoriesUseCase,
                            private val returnRepositoriesUseCase: ReturnRepositoriesUseCase,
                            private val fetchRepositoriesLiveDataUseCase: FetchRepositoriesLiveDataUseCase): ViewModel(){
    val onSuccess = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    //val repositoriesLiveData = returnRepositoriesUseCase.returnRepositories()
    private var job: Job? = null

    fun fetchRepositoriesLiveData(): LiveData<PagingData<RepositoriesItem>> {
        return fetchRepositoriesLiveDataUseCase.fetchRepositoriesLiveData()
            .cachedIn(viewModelScope)
    }

    /*
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
     */
    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}