package com.example.aritra_agilepoint.webservices

import com.example.aritra_agilepoint.modules.data.Employee
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import java.util.ArrayList

interface APICall {
    @GET("employees")
    abstract fun getAllEmployees(): Observable<ArrayList<Employee>>

    @GET("employee/:id")
    abstract fun getOneEmployees(id: String): Single<Employee>
}