package com.example.aritra_agilepoint.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.aritra_agilepoint.R
import com.example.aritra_agilepoint.common.Resource
import com.example.aritra_agilepoint.modules.data.Employee
import com.example.aritra_agilepoint.viewmodel.EmployeeVM
import org.jetbrains.anko.find

class EmployeeDetailActivity : BaseActivity() {

    lateinit var ivEmployee: ImageView
    lateinit var tvEmployeeName: TextView
    lateinit var tvEmployeeAge: TextView
    lateinit var tvEmployeeSalary: TextView

    lateinit var employeeVM: EmployeeVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)

        initialiseControls()

        employeeVM = ViewModelProviders.of(this).get(EmployeeVM::class.java)
        employeeVM.getSingleEmployeeMutable()?.let {
            it.observe(this, Observer {it ->
                when(it.status) {
                    Resource.SUCCESS -> {
                        it.data?.let {
                            tvEmployeeName.text = it.employee_name
                            tvEmployeeAge.text = it.employee_age
                            tvEmployeeSalary.text = it.employee_salary

                        } ?.run {
                        }
                    }
                    Resource.ERROR -> {

                    }
                }
            })
        }
        employeeVM.loadOneEmployee(intent.getStringExtra("EMPLOYEE_ID"))
    }

    private fun initialiseControls() {
        ivEmployee = find(R.id.ivEmployee)
        tvEmployeeName = find(R.id.tvEmployeeName)
        tvEmployeeAge = find(R.id.tvEmployeeAge)
        tvEmployeeSalary = find(R.id.tvEmployeeSalary)
    }
}
