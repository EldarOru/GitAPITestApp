package com.example.gitapitestapp.presentation.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gitapitestapp.domain.internet.RetrofitServices
import com.example.gitapitestapp.domain.internet.RetrofitServices.Companion.MAGIC_NUMBER
import com.example.gitapitestapp.domain.models.RepositoriesItem

class RepositoriesDataSource(private val retrofitServices: RetrofitServices): PagingSource<Int, RepositoriesItem>() {
    override fun getRefreshKey(state: PagingState<Int, RepositoriesItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoriesItem> {
        val nextNum = params.key ?: DEFAULT_PAGE
        return try {
            val response = retrofitServices.getRepositories(MAGIC_NUMBER + 100 * nextNum).body() as ArrayList<RepositoriesItem>
            LoadResult.Page(
                data = response,
                prevKey = if (nextNum > 0) nextNum - 1 else null,
                nextKey = if (nextNum < 10) nextNum + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    companion object{
        private const val MAGIC_NUMBER = 265165
        private const val DEFAULT_PAGE = 0
    }
}