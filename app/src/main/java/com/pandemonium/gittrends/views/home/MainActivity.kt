package com.pandemonium.gittrends.views.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandemonium.gittrends.databinding.ActivityMainBinding
import com.pandemonium.gittrends.service.models.ReposItem
import com.pandemonium.gittrends.views.home.adapters.TrendRepoAdapter
import com.pandemonium.gittrends.views.home.viewmodels.MainViewModel

class MainActivity : AppCompatActivity(), TrendRepoAdapter.AdapterCallback {

    lateinit var binding: ActivityMainBinding
    lateinit var viewmodel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        viewmodel = ViewModelProvider(this)[MainViewModel::class.java]

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRepos.apply {
            adapter = TrendRepoAdapter(this@MainActivity)
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        viewmodel.getTrendingRepos()

        attachObserver()

        if(viewmodel.selectedPosition.value != -1)
        binding.rvRepos.apply {
            post {
                viewmodel.selectedPosition.value?.let { layoutManager?.scrollToPosition(it) }
            }
        }
    }

    private fun attachObserver() {

        viewmodel.selectedPosition.observe(this, object : Observer<Int> {
            override fun onChanged(t: Int?) {
                (binding.rvRepos.adapter as TrendRepoAdapter).apply {
                    selectedPos = t!!
                }
            }
        })


        viewmodel.trendingReposList.observe(this, object : Observer<List<ReposItem>> {
            override fun onChanged(t: List<ReposItem>?) {
                (binding.rvRepos.adapter as TrendRepoAdapter).apply {
                    trendingRepoList = t!!
                    notifyDataSetChanged()
                }
            }

        })
    }

    override fun onItemPressed(pos: Int) {
        viewmodel.selectedPosition.value = pos
    }
}