package com.example.aritra_agilepoint.common

import android.app.Application
import com.example.aritra_agilepoint.webservices.RetrofitService
import okhttp3.OkHttpClient

class AppInstance  : Application() {

//    var retrofit: Retrofit
//    private var apiCalls: ApiCalls? = null
    private var client: OkHttpClient? = null
    override fun onCreate() {
        super.onCreate()

//        initRetrofit()
    }

//    private fun initRetrofit() {
//        initHTTPClient()
//        retrofit = RetrofitService.Builder()
//            .baseUrl(ZPEndPoints.get(API_BASE_URL)/*BuildConfig.BASE_URL*/)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        apiCalls = retrofit.create(ApiCalls::class.java)
//    }
}