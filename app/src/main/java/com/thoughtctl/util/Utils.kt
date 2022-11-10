package com.thoughtctl.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.thoughtctl.app.MyApplication
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun hasInternetConnection(application: MyApplication): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun changeDateFormat(my_date:String): String
    {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val strDate = sdf.parse(my_date)
            val formatter = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
            return formatter.format(strDate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getDateFromTimestamp(milliSeconds: Long): String? {
        // Create a DateFormatter object for displaying date in specified
        // format.
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.time)
    }
}