package com.pandemonium.gittrends.views.home.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandemonium.gittrends.service.models.ReposItem
import com.pandemonium.gittrends.service.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val trendingReposList =  MutableLiveData<List<ReposItem>>()
    val selectedPosition = MutableLiveData<Int>(-1)

    fun getTrendingRepos(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = RetrofitInstance.Instance.getTrendingRepositories()

            if(result.isSuccessful){
                trendingReposList.postValue(result.body())
            } else {
                trendingReposList.postValue(ArrayList())
            }
        }
    }

}