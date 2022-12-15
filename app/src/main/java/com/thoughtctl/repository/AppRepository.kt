package com.thoughtctl.repository

import com.thoughtctl.network.RetrofitInstance

class AppRepository {
    var type : String = "jpg"
    var search : String = ""
    suspend fun getImages() = RetrofitInstance.imagesAPI.getImages(search,type)
    suspend fun getNormalImages() = RetrofitInstance.imagesAPI.getNormalImages(search,type)
}