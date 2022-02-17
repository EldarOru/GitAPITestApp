package com.example.gitapitestapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitapitestapp.databinding.RepositoryItemBinding
import com.example.gitapitestapp.domain.models.RepositoriesItem
import kotlinx.coroutines.NonDisposableHandle.parent

class RepositoriesPagedAdapter(private val context: Context):
    PagingDataAdapter<RepositoriesItem, RepositoriesPagedAdapter.RepositoriesPagedHolder>(
        DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: RepositoriesPagedHolder, position: Int) {
        val rep: RepositoriesItem? = getItem(position)
        holder.repositoryBinging.apply {
            fullNameTv.text = rep?.full_name
            loginTv.text = rep?.owner?.login
            Glide.with(context).load(rep?.owner?.avatar_url).into(avatarIv)
            /*
            root.setOnClickListener {
                onRepositoryClickListener?.invoke(rep)
            }

             */
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesPagedHolder {
        val jokeView =
            RepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoriesPagedHolder(jokeView)
    }

    class RepositoriesPagedHolder(val repositoryBinging: RepositoryItemBinding) :
        RecyclerView.ViewHolder(repositoryBinging.root) {

    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<RepositoriesItem>() {
            // Concert details may have changed if reloaded from the database,
            // but ID is fixed.
            override fun areItemsTheSame(
                oldConcert: RepositoriesItem,
                newConcert: RepositoriesItem
            ) = oldConcert.id == newConcert.id

            override fun areContentsTheSame(
                oldConcert: RepositoriesItem,
                newConcert: RepositoriesItem
            ) = oldConcert == newConcert
        }
    }
}



