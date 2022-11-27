package com.pandemonium.gittrends.views.home.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandemonium.gittrends.service.models.ReposItem
import com.pandemonium.gittrends.service.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val trendingReposList = MutableLiveData<List<ReposItem>>()
    private val trendingReposListMain = MutableLiveData<List<ReposItem>>()
    val selectedPosition = MutableLiveData<Int>(-1)
    val searchText = MutableLiveData<String>("")

    fun getTrendingRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = RetrofitInstance.Instance.getTrendingRepositories()

            if (result.isSuccessful) {
                trendingReposList.postValue(result.body())
                trendingReposListMain.postValue(result.body())
            } else {
                trendingReposList.postValue(ArrayList())
                trendingReposListMain.postValue(ArrayList())
            }
        }
    }

    fun filterRepos(text: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            if (text.isNullOrEmpty()) {
                trendingReposList.postValue(trendingReposListMain.value)
            } else {
                var tempList = ArrayList<ReposItem>()

                for (repo in trendingReposListMain.value!!) {
                    if (textInRepo(repo, text))
                        tempList.add(repo)
                }

                trendingReposList.postValue(tempList)
            }
        }
    }

    private fun textInRepo(repo: ReposItem, text: String): Boolean {

        if (repo.author?.lowercase()?.contains(text) == true ||
            repo.name?.lowercase()?.contains(text) == true ||
            repo.description?.lowercase()?.contains(text) == true ||
            repo.language?.lowercase()?.contains(text) == true
        )

            return true

        return false
    }

}