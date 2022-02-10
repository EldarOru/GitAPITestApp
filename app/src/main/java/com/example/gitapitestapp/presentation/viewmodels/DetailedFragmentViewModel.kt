package com.example.gitapitestapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gitapitestapp.domain.models.ComItem
import com.example.gitapitestapp.domain.usecases.GetCommitsUseCase
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException

class DetailedFragmentViewModel(private val getCommitsUseCase: GetCommitsUseCase): ViewModel() {

    val commitsLiveData = MutableLiveData<ArrayList<ComItem>>()
    val errorMessage = MutableLiveData<String>()

    private var jobOne: Job? = null

    fun getCommits(owner: String, repo: String){
        jobOne = CoroutineScope(Dispatchers.IO).launch {
            val response: Response<ArrayList<ComItem>> = try {
                getCommitsUseCase.getCommits(owner, repo)
            } catch (e: Exception){
                withContext(Dispatchers.Main) {
                    onError("Error : ${e.localizedMessage} ")
                }
                return@launch
            }
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    commitsLiveData.postValue(response.body())
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }

    override fun onCleared() {
        super.onCleared()
        jobOne?.cancel()
    }
}