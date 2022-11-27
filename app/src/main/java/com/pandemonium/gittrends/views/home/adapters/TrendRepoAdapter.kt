package com.pandemonium.gittrends.views.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandemonium.gittrends.databinding.VhTrendingRepoBinding
import com.pandemonium.gittrends.service.models.ReposItem
import com.pandemonium.gittrends.views.home.viewholders.TrendReposViewHolder

class TrendRepoAdapter(var callback: AdapterCallback): RecyclerView.Adapter<TrendReposViewHolder>(), TrendReposViewHolder.HolderCallback {

    var trendingRepoList: List<ReposItem> = ArrayList<ReposItem>()
    var selectedPos: Int = -1
    var selectedItem: ReposItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendReposViewHolder {
        val binding = VhTrendingRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendReposViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: TrendReposViewHolder, position: Int) {
//        (holder as TrendReposViewHolder).setData(trendingRepoList[position], selectedPos == position, position)
        (holder as TrendReposViewHolder).setData(trendingRepoList[position], trendingRepoList[position] == selectedItem, selectedItem)
    }

    override fun getItemCount(): Int {
        return trendingRepoList.size
    }

    override fun onItemPressed(pos: Int) {
        selectedPos = pos
        notifyDataSetChanged()
        callback.onItemPressed(pos)
    }

    override fun onItemPressed(item: ReposItem?) {
        selectedItem = item
        notifyDataSetChanged()
        callback.onItemPressed(item)
    }

    interface AdapterCallback{
        fun onItemPressed(pos: Int)
        fun onItemPressed(item: ReposItem?)
    }
}