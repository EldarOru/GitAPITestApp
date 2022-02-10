package com.example.gitapitestapp.domain.internet

import com.example.gitapitestapp.domain.models.ComItem
import com.example.gitapitestapp.domain.models.RepositoriesItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


interface RetrofitServices {
    @GET("repositories")
    suspend fun getRepositories(@Query("since") number: Int): Response<ArrayList<RepositoriesItem>>

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommits(@Path("owner") owner: String, @Path("repo") repo: String): Response<ArrayList<ComItem>>

    companion object{
        var okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        var retrofitService: RetrofitServices? = null
        fun getInstance() : RetrofitServices {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitServices::class.java)
            }
            return retrofitService!!
        }

        const val MAGIC_NUMBER = 265165
        private const val BASE_URL = "https://api.github.com/"
    }
}