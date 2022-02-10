package com.example.gitapitestapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitapitestapp.databinding.RepositoryItemBinding
import com.example.gitapitestapp.databinding.ShaItemBinding
import com.example.gitapitestapp.domain.models.Parent
import com.example.gitapitestapp.domain.models.RepositoriesItem

class ShaListAdapter: RecyclerView.Adapter<ShaListAdapter.ShaItemViewHolder>() {

    var list = listOf<Parent>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShaItemViewHolder {
        val shaView = ShaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShaItemViewHolder(shaView)
    }

    override fun onBindViewHolder(holder: ShaItemViewHolder, position: Int) {
        val parent: Parent = list[position]
        holder.shaItemBinding.apply {
            shaTv.text = parent.sha
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ShaItemViewHolder(val shaItemBinding: ShaItemBinding): RecyclerView.ViewHolder(shaItemBinding.root)
}