package com.pandemonium.gittrends.service.network

import com.pandemonium.gittrends.service.models.ReposItem
import com.pandemonium.gittrends.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @Headers(
        "X-RapidAPI-Key: ${Constants.API_KEY}",
        "X-RapidAPI-Host: ${Constants.HOST}"
    )
    @GET("/repositories")
    suspend fun getTrendingRepositories() : Response<List<ReposItem>>


}