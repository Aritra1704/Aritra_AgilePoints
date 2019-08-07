package com.example.aritra_agilepoint.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aritra_agilepoint.R
import com.example.aritra_agilepoint.common.Resource
import com.example.aritra_agilepoint.modules.data.Employee
import com.example.aritra_agilepoint.viewmodel.EmployeeVM
import com.example.aritra_agilepoints.ui.adapters.EmployeeAdapters
import com.google.android.material.floatingactionbutton.FloatingActionButton

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

class EmployeeListActivity : AppCompatActivity() {

    lateinit var rvEmployee: RecyclerView
    lateinit var tvNoData: TextView
    lateinit var pbLoading: ProgressBar
    lateinit var fab: FloatingActionButton
    lateinit var adapter: EmployeeAdapters

    lateinit var employeeVM: EmployeeVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initialiseControls()

        employeeVM = ViewModelProviders.of(this).get(EmployeeVM::class.java)
        employeeVM.getEmployeesMutable()?.let {
            it.observe(this, Observer {it ->
                when(it.status) {
                    Resource.SUCCESS -> {
                        it.data?.let {
                            adapter.refresh(it)
                            tvNoData.visibility = View.GONE
                        } ?.run {
                            tvNoData.visibility = View.VISIBLE
                            rvEmployee.visibility = View.GONE
                        }
                        pbLoading.visibility = View.GONE
                    }
                    Resource.ERROR -> {
                        tvNoData.visibility = View.VISIBLE
                        rvEmployee.visibility = View.GONE
                        pbLoading.visibility = View.GONE
                    }
                }
            })
        }
        employeeVM.loadEmployees()

        setAdapter()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun setAdapter() {
        adapter = EmployeeAdapters(this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        rvEmployee.setLayoutManager(mLayoutManager)
        rvEmployee.setItemAnimator(DefaultItemAnimator())
        rvEmployee.setAdapter(adapter)
    }

    fun initialiseControls() {
        rvEmployee = find(R.id.rvEmployee)
        tvNoData = find(R.id.tvNoData)
        pbLoading = find(R.id.pbLoading)
        fab = find(R.id.fab)
    }
}
