package com.cst3115.enterprise.employeemanagementsystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.cst3115.enterprise.employeemanagementsystem.data.local.Employee
import com.cst3115.enterprise.employeemanagementsystem.data.local.Department
import com.cst3115.enterprise.employeemanagementsystem.data.repository.EmployeeRepository

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*
Acts as intermediary between UI and the repository
 */

class EmployeeViewModel (
    private val repository: EmployeeRepository
    ) : ViewModel() {

        // Observe employees
        val employees = repository.allEmployees.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Observe departments
        val departments = repository.allDepartments.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Insert employee
        fun addEmployee(name: String, email: String, deptID: Int) {

            viewModelScope.launch {

                val employee = Employee(
                    name = name,
                    email = email,
                    deptID = deptID
                )

                repository.insertEmployee(employee)
            }
        }

        // Insert department
        fun addDepartment(name: String) {

            viewModelScope.launch {

                val department = Department(
                    name = name
                )

                repository.insertDepartment(department)
            }
        }

        // Delete employee
        fun deleteEmployee(employee: Employee) {

            viewModelScope.launch {
                repository.deleteEmployee(employee)
            }
        }

        // Delete department
        fun deleteDepartment(department: Department) {

            viewModelScope.launch {
                repository.deleteDepartment(department)
            }
        }
}