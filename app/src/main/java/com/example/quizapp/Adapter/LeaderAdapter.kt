package com.example.quizapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quizapp.Domain.UserModel
import com.example.quizapp.databinding.ViewholderLeaderBinding

class LeaderAdapter : RecyclerView.Adapter<LeaderAdapter.ViewHolder>() {

    private lateinit var binding: ViewholderLeaderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ViewholderLeaderBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: LeaderAdapter.ViewHolder, position: Int) {
        val binding = ViewholderLeaderBinding.bind(holder.itemView)
        binding.titleTxt.text = differ.currentList[position].name


        val drawableResourcesId: Int = binding.root.resources.getIdentifier(
            differ.currentList[position].pic,
            "drawable",binding.root.context.packageName
        )

        Glide.with(binding.root.context)
            .load(drawableResourcesId)
            .into(binding.imageCircle)

        binding.textView10.text = (position+4).toString()
        binding.scoreTxt.text = differ.currentList[position].score.toString()
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)

    private val differCallback = object :DiffUtil.ItemCallback<UserModel>(){
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
}