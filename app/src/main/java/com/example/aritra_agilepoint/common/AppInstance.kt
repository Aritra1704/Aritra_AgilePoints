package com.example.aritra_agilepoint.common

import android.app.Application

class AppInstance  : Application() {

//    val component: AppComponent by lazy {
//        DaggerAppComponent
//            .builder()
//            .retrofitModule(RetrofitModule())
//            .build()
//    }

    override fun onCreate() {
        super.onCreate()

//        component.inject(this)
    }
}