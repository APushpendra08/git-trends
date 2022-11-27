package com.pandemonium.gittrends.views.home

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandemonium.gittrends.databinding.ActivityMainBinding
import com.pandemonium.gittrends.service.models.ReposItem
import com.pandemonium.gittrends.utils.Constants
import com.pandemonium.gittrends.views.home.adapters.TrendRepoAdapter
import com.pandemonium.gittrends.views.home.viewmodels.MainViewModel


class MainActivity : AppCompatActivity(), TrendRepoAdapter.AdapterCallback {

    lateinit var binding: ActivityMainBinding
    lateinit var viewmodel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {

        viewmodel = ViewModelProvider(this)[MainViewModel::class.java]

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        attachObserver()

        fetchData()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun fetchData() {

        if (viewmodel.trendingReposList.value.isNullOrEmpty()) {
            if (checkInternetConnectivity()) {
                binding.llNoInternet.isVisible = false
                viewmodel.setLoaderState(Constants.LOADING)
                viewmodel.getTrendingRepos()
            } else {
                viewmodel.setLoaderState(Constants.NOT_LOADING)
                binding.llNoInternet.isVisible = true
                toast("Please connect to internet and try again")
            }
        }

    }

    private fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {

        // Recyclerview
        binding.rvRepos.apply {
            adapter = TrendRepoAdapter(this@MainActivity)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        // SearchView
        binding.svRepos.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewmodel.setSearchText(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewmodel.setSearchText(newText)
                return false
            }

        })

        //No Internet ll
        binding.llNoInternet.setOnClickListener {
            fetchData()
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

        viewmodel.selectedItemObject.observe(this, object : Observer<ReposItem?> {
            override fun onChanged(t: ReposItem?) {
                (binding.rvRepos.adapter as TrendRepoAdapter).apply {
                    selectedItem = t
                }
            }

        })

        viewmodel.trendingReposList.observe(this, object : Observer<List<ReposItem>?> {
            override fun onChanged(t: List<ReposItem>?) {
                (binding.rvRepos.adapter as TrendRepoAdapter).apply {
                    t?.let {
                        trendingRepoList = t
                        notifyDataSetChanged()
                    }
                }
            }

        })

        viewmodel.searchText.observe(this, object : Observer<String?> {
            override fun onChanged(t: String?) {
                viewmodel.filterRepos(t);
            }

        })

        viewmodel.loaderState.observe(this, object : Observer<Int> {
            override fun onChanged(t: Int?) {

                binding.pbLoader.isVisible = (t == Constants.LOADING)

            }

        })

        if (viewmodel.selectedPosition.value != -1) {
            binding.rvRepos.apply {
                post {
                    viewmodel.selectedPosition.value?.let { layoutManager?.scrollToPosition(it) }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkInternetConnectivity(): Boolean {
        val connectivityManager =
            (this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager)

        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }

        return false
    }

    override fun onItemPressed(pos: Int) {
        viewmodel.setSelectedPosition(pos)
    }

    override fun onItemPressed(item: ReposItem?) {
        viewmodel.setSelectedItemObject(item)
    }

}