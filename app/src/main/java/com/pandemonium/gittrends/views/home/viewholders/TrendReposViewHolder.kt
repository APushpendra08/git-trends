package com.pandemonium.gittrends.views.home.viewholders

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.pandemonium.gittrends.databinding.VhTrendingRepoBinding
import com.pandemonium.gittrends.service.models.ReposItem

class TrendReposViewHolder(var binding: VhTrendingRepoBinding, var callback: HolderCallback): RecyclerView.ViewHolder(binding.root) {

    fun setData(data: ReposItem, isSelected: Boolean, pos: Int){


        binding.root.setOnClickListener {
            callback.onItemPressed(pos)
        }

        binding.root.setBackgroundColor(
            if (isSelected) Color.CYAN else Color.WHITE
        )

        binding.tvAuthor.text = data.author
        binding.tvName.text = data.name
        binding.tvDesc.text = data.description

        binding.tvLanguage.text = data.language
        data.languageColor?.let {
            binding.tvLanguage.setTextColor(Color.parseColor(it))
        }

        binding.tvForks.text = data.forks?.toString()
        binding.tvStars.text = data.stars?.toString()
    }

    interface HolderCallback{
        fun onItemPressed(pos: Int)
    }

}