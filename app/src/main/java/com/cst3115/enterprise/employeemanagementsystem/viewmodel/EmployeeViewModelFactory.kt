package com.cst3115.enterprise.employeemanagementsystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.cst3115.enterprise.employeemanagementsystem.data.repository.EmployeeRepository

class EmployeeViewModelFactory(
    private val repository: EmployeeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            return EmployeeViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

