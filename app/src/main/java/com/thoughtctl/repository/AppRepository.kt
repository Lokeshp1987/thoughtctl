package com.thoughtctl.repository

import com.thoughtctl.network.RetrofitInstance

class AppRepository {

    var type : String = "jpg"
    var search : String = ""
    suspend fun getClosedIssues() = RetrofitInstance.githubApi.getIssues()
    suspend fun getImages() = RetrofitInstance.githubApi.getImages(search,type)


}