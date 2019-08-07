package com.example.aritra_agilepoints.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aritra_agilepoint.R
import com.example.aritra_agilepoint.modules.data.Employee
import com.example.aritra_agilepoint.ui.EmployeeDetailActivity
import org.jetbrains.anko.find

class EmployeeAdapters(val context: Context) : RecyclerView.Adapter<EmployeeAdapters.EmployeeHolder>() {
    private lateinit var employeeList:List<Employee>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapters.EmployeeHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_employee, parent, false)
        return EmployeeHolder(itemView)
    }

    fun refresh(users: List<Employee>) {
        this.employeeList = users
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EmployeeAdapters.EmployeeHolder, position: Int) {
        holder.tvEmployeeName.text = employeeList.get(position).employee_name
        holder.tvEmployeeAge.text = employeeList.get(position).employee_age
        holder.tvEmployeeSalary.text = "Salary: ${employeeList.get(position).employee_salary}"
        holder.myView.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(context, EmployeeDetailActivity::class.java)
            intent.putExtra("EMPLOYEE_ID", employeeList.get(position).id)
            context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return if(::employeeList.isInitialized) employeeList.size else 0
    }

    inner class EmployeeHolder(internal var myView: View) : RecyclerView.ViewHolder(myView) {

        var tvEmployeeName: TextView
        var tvEmployeeAge: TextView
        var tvEmployeeSalary: TextView
        var ivEmployee: ImageView

        init {
            tvEmployeeName = myView.find(R.id.tvEmployeeName)
            tvEmployeeAge = myView.find(R.id.tvEmployeeAge)
            tvEmployeeSalary = myView.find(R.id.tvEmployeeSalary)
            ivEmployee = myView.find(R.id.ivEmployee)
        }
    }
}