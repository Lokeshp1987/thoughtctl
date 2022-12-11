package com.thoughtctl.network

import com.thoughtctl.util.Constants.BASE_URL
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitInstance {
    companion object {

        private val imagesRetrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val original: Request = chain.request()
                        val requestBuilder : Request.Builder = original.newBuilder().addHeader("Content-Type", "application/json")
                        requestBuilder.addHeader("Authorization", "Client-ID 7c20820e4252d22")
                        val request: Request = requestBuilder.build()
                        return chain.proceed(request)
                    } }).build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val imagesAPI by lazy {
            imagesRetrofit.create(API::class.java)
        }
    }
}