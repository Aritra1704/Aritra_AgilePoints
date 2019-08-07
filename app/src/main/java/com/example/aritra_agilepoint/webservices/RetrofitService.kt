package com.example.aritra_agilepoint.webservices

import android.content.Context
import com.example.aritra_agilepoint.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class RetrofitService() {
    public var retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(getOkHttpClient(getHttpLoggingInterceptor()))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    public fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

    private fun getOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
//        val clientBuilder = OkHttpClient.Builder()
//        val cacheDir = File(mContext.applicationContext.cacheDir, "someChildName")
//        val DISK_CACHE_SIZE: Long = 50 * 1024 * 1024 // 50MB
//        val cache = Cache(cacheDir, DISK_CACHE_SIZE)
//        clientBuilder.cache(cache).build()

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE//add None for prod env
        return interceptor
    }
}