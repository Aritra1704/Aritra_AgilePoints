package com.example.aritra_agilepoint.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.aritra_agilepoint.common.Resource
import com.example.aritra_agilepoint.modules.data.Employee

class EmployeeVM : BaseVM() {

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
    }

    fun loadOneEmployee(employeeId: String) {
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
