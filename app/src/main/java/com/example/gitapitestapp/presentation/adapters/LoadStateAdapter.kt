package com.example.gitapitestapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapitestapp.databinding.LoaderBinding

class LoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<com.example.gitapitestapp.presentation.adapters.LoadStateAdapter.LoaderViewHolder>() {

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.loaderBinding.loadStateErrorMessage.isVisible = loadState !is LoadState.Loading
        holder.loaderBinding.loadStateProgress.isVisible = loadState is LoadState.Loading
        holder.loaderBinding.loadStateRetry.isVisible = loadState !is LoadState.Loading

        if (loadState is LoadState.Error){
            holder.loaderBinding.loadStateErrorMessage.text = loadState.error.localizedMessage
            //holder.loaderBinding.loadStateProgress.isVisible = false
        }

        holder.loaderBinding.loadStateRetry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val loader = LoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoaderViewHolder(loader)
    }

    class LoaderViewHolder(val loaderBinding: LoaderBinding) : RecyclerView.ViewHolder(loaderBinding.root) {

    }
}

