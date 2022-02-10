package com.example.gitapitestapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitapitestapp.databinding.RepositoryItemBinding
import com.example.gitapitestapp.domain.models.RepositoriesItem

class RepositoriesListAdapter(private val context: Context): RecyclerView.Adapter<RepositoriesListAdapter.RepositoryItemViewHolder>() {

    var onRepositoryClickListener: ((RepositoriesItem) -> Unit)? = null

    var list = listOf<RepositoriesItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        val jokeView = RepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryItemViewHolder(jokeView)
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        val rep: RepositoriesItem = list[position]
        holder.repositoryBinging.apply {
            fullNameTv.text = rep.full_name
            loginTv.text = rep.owner.login
            Glide.with(context).load(rep.owner.avatar_url).into(avatarIv)
            root.setOnClickListener {
                onRepositoryClickListener?.invoke(rep)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class RepositoryItemViewHolder(val repositoryBinging: RepositoryItemBinding): RecyclerView.ViewHolder(repositoryBinging.root)
}