package com.example.aritra_agilepoint.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.aritra_agilepoint.common.Resource
import com.example.aritra_agilepoint.modules.data.Employee
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EmployeeVM : BaseVM() {

    private lateinit var subscription: Disposable

    private var employeeListLD: MutableLiveData<Resource<List<Employee>>>? = null
    private var employeeLD: MutableLiveData<Resource<Employee>>? = null


    fun getEmployeesMutable(): MutableLiveData<Resource<List<Employee>>>? {
        employeeListLD = MutableLiveData()
        return employeeListLD
    }

    fun getSingleEmployeeMutable(): MutableLiveData<Resource<Employee>>? {
        employeeLD = MutableLiveData()
        return employeeLD
    }

    fun loadEmployees() {
        subscription = apiCall.getAllEmployees()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onApiLoading("GET_ALL") }
            .subscribe(
                { result -> onEmployeesSuccess(result) },
                { error -> onEmployeesError(error) }
            )
    }

    fun loadOneEmployee(employeeId: String) {
        subscription = apiCall.getOneEmployees(employeeId)
            .toFlowable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onApiLoading("GET_ONE") }
            .subscribe(
                { result -> onSingleEmployeesSuccess(result) },
                { error -> onSingleEmployeesError(error) }
            )
    }

    private fun onApiLoading(from: String){
        when(from) {
            "GET_ALL" -> employeeListLD!!.value = Resource.loading(null)
            "GET_ONE" -> employeeLD!!.value = Resource.loading(null)
        }
    }

    private fun onEmployeesSuccess(employees:List<Employee>){
        employeeListLD!!.value = Resource.success(employees)
    }

    private fun onEmployeesError(error: Throwable){
        employeeListLD!!.value = Resource.error(error.message, null)
    }

    private fun onSingleEmployeesSuccess(employees:Employee){
        employeeLD!!.value = Resource.success(employees)
    }

    private fun onSingleEmployeesError(error: Throwable){
        employeeLD!!.value = Resource.error(error.message, null)

    }
}
