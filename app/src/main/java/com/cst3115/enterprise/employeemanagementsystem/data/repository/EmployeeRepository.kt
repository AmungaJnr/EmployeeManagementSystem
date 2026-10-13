package com.cst3115.enterprise.employeemanagementsystem.data.repository

import com.cst3115.enterprise.employeemanagementsystem.data.local.Employee
import com.cst3115.enterprise.employeemanagementsystem.data.local.Department
import com.cst3115.enterprise.employeemanagementsystem.data.local.EmployeeDAO
import com.cst3115.enterprise.employeemanagementsystem.data.local.DepartmentDAO

// Constructor injection
class EmployeeRepository(
    private val employeeDao: EmployeeDAO,
    private val departmentDao: DepartmentDAO
) {

    // Observe all employees
    val allEmployees = employeeDao.getAllEmployees()

    // Observe all departments
    val allDepartments = departmentDao.getAllDepartments()

    // Add an employee
    suspend fun insertEmployee(employee: Employee) {
        employeeDao.insertEmployee(employee)
    }

    // Delete an employee
    suspend fun deleteEmployee(employee: Employee) {
        employeeDao.deleteEmployee(employee)
    }

    // Add a department
    suspend fun insertDepartment(department: Department) {
        departmentDao.insertDepartment(department)
    }

    // Delete a department
    suspend fun deleteDepartment(department: Department) {
        departmentDao.deleteDepartment(department)
    }

    // Observe a specific employee
    fun getEmployeeById(employeeId: Int) =
        employeeDao.getEmployeeById(employeeId)
}