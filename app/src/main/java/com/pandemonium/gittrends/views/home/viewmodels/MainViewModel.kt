package com.pandemonium.gittrends.views.home.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandemonium.gittrends.service.models.ReposItem
import com.pandemonium.gittrends.service.network.RetrofitInstance
import com.pandemonium.gittrends.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _trendingReposList = MutableLiveData<List<ReposItem>?>()
    val trendingReposList: LiveData<List<ReposItem>?> = _trendingReposList

    private val trendingReposListMain = MutableLiveData<List<ReposItem>>()

    private val _selectedPosition = MutableLiveData<Int>(-1)
    val selectedPosition: LiveData<Int> = _selectedPosition

    private val _selectedItemObject = MutableLiveData<ReposItem?>(null)
    val selectedItemObject: LiveData<ReposItem?> = _selectedItemObject

    private val _searchText = MutableLiveData<String?>("")
    val searchText: LiveData<String?> = _searchText

    private val _loaderState = MutableLiveData<Int>(Constants.LOADING)
    val loaderState: LiveData<Int> = _loaderState

    fun getTrendingRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = RetrofitInstance.Instance.getTrendingRepositories()

            if (result.isSuccessful) {
                _trendingReposList.postValue(result.body())
                trendingReposListMain.postValue(result.body())
            } else {
                _trendingReposList.postValue(ArrayList())
                trendingReposListMain.postValue(ArrayList())
            }
            _loaderState.postValue(Constants.NOT_LOADING)
        }
    }

    fun setLoaderState(state: Int){
        _loaderState.value = state
    }

    fun filterRepos(text: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            if (text.isNullOrEmpty()) {
                _trendingReposList.postValue(trendingReposListMain.value)
            } else {
                var tempList = ArrayList<ReposItem>()

                for (repo in trendingReposListMain.value!!) {
                    if (checkTextInRepo(repo, text))
                        tempList.add(repo)
                }

                _trendingReposList.postValue(tempList)
            }
        }
    }

    private fun checkTextInRepo(repo: ReposItem, text: String): Boolean {

        if (repo.author?.lowercase()?.contains(text) == true ||
            repo.name?.lowercase()?.contains(text) == true ||
            repo.description?.lowercase()?.contains(text) == true ||
            repo.language?.lowercase()?.contains(text) == true
        )

            return true

        return false
    }

    fun setSelectedPosition(pos: Int) {
        _selectedPosition.value = pos
    }

    fun setSelectedItemObject(item: ReposItem?) {
        _selectedItemObject.value = item
    }

    fun setSearchText(text: String?) {
        _searchText.value = text
    }


}