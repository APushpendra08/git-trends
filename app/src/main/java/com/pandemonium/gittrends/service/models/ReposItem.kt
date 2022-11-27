package com.pandemonium.gittrends.service.models

data class ReposItem(
    val author: String?,
    val avatar: String?,
    val builtBy: List<BuiltBy>?,
    val currentPeriodStars: Int?,
    val description: String?,
    val forks: Int?,
    val language: String?,
    val languageColor: String?,
    val name: String?,
    val stars: Int?,
    val url: String?,
)