package com.example.aritra_agilepoint.modules

import android.content.Context
import com.example.aritra_agilepoint.webservices.APICall
import com.example.aritra_agilepoint.webservices.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
object RetrofitModule {
    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun providePostApi(retrofit: Retrofit): APICall {
        return retrofit.create(APICall::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return RetrofitService().retrofit
    }
}