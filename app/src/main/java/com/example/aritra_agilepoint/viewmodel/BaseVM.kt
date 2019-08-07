package com.example.aritra_agilepoint.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aritra_agilepoint.modules.DaggerAppComponent
import com.example.aritra_agilepoint.webservices.APICall
import javax.inject.Inject

abstract class BaseVM : ViewModel() {
    @Inject
    protected lateinit var apiCall: APICall

    private val injector: AppComponent = DaggerAppComponent
        .builder()
        .networkModule(RetrofitModule)
        .build()

    init {
        injector.inject(this)
    }

}