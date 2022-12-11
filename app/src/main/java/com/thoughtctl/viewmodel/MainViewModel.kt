package com.thoughtctl.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thoughtctl.R
import com.thoughtctl.app.MyApplication
import com.thoughtctl.model.GaleryArray
import com.thoughtctl.model.ImagesResponse
import com.thoughtctl.repository.AppRepository
import com.thoughtctl.util.Resource
import com.thoughtctl.util.Utils.hasInternetConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    val issuesData: MutableLiveData<Resource<ImagesResponse>> = MutableLiveData()



    fun getIssues() = viewModelScope.launch {
        fetchIssues()
    }

    fun setSearchValue(searchText : String)
    {
        appRepository.search = searchText
    }
    private suspend fun fetchIssues() {
        issuesData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.getImages()
                issuesData.postValue(handlePicsResponse(response))
            } else {
                issuesData.postValue(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection)))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> issuesData.postValue(
                    Resource.Error(
                        getApplication<MyApplication>().getString(
                            R.string.network_failure
                        )
                    )
                )
                else -> issuesData.postValue(
                    Resource.Error(
                        getApplication<MyApplication>().getString(
                            R.string.conversion_error
                        )
                    )
                )
            }
        }
    }

    private fun handlePicsResponse(response: Response<ImagesResponse>): Resource<ImagesResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}