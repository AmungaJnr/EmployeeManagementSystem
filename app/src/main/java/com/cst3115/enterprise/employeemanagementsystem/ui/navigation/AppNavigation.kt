package com.cst3115.enterprise.employeemanagementsystem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.produceState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

import com.cst3115.enterprise.employeemanagementsystem.ui.screens.HomeScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.AddDepartmentScreen
import com.cst3115.enterprise.employeemanagementsystem.viewmodel.EmployeeViewModel
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.AddEmployeeScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.EmployeeDetailsScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.DeleteEmployeeScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.DeleteDepartmentScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.SplashScreen

@Composable
fun AppNavigation(
    viewModel: EmployeeViewModel,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    val departments by viewModel.departments.collectAsStateWithLifecycle()

    val employees by viewModel.employees.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH,
        modifier = modifier
    ) {

        // Splash Screen
        composable(AppRoutes.SPLASH) {

            SplashScreen(
                onSplashFinished = {

                    navController.navigate(AppRoutes.HOME) {

                        popUpTo(AppRoutes.SPLASH) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        // Home Screen
        composable(AppRoutes.HOME) {

            HomeScreen(
                viewModel = viewModel,

                onAddDepartment = {
                    navController.navigate(AppRoutes.ADD_DEPARTMENT)
                },

                onAddEmployee = {
                    navController.navigate(AppRoutes.ADD_EMPLOYEE)
                },

                onEmployeeClick = { employeeId ->
                    navController.navigate(
                        "employee_details/$employeeId"
                    )
                },

                onDeleteEmployee = {
                    navController.navigate(AppRoutes.DELETE_EMPLOYEE)
                },

                onDeleteDepartment = {
                    navController.navigate(AppRoutes.DELETE_DEPARTMENT)
                }
            )
        }

        // Add department
        composable(AppRoutes.ADD_DEPARTMENT) {

            AddDepartmentScreen(
                onSaveDepartment = { name ->

                    viewModel.addDepartment(name)

                    navController.popBackStack()
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Add employee
        composable(AppRoutes.ADD_EMPLOYEE){

            AddEmployeeScreen(
                departments = departments,

                onSaveEmployee = { name, email, deptID ->

                    viewModel.addEmployee(
                        name = name,
                        email = email,
                        deptID = deptID
                    )

                    navController.popBackStack()
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Employee Details
        composable(AppRoutes.EMPLOYEE_DETAILS) { backStackEntry ->

            val employeeId = backStackEntry.arguments
                ?.getString("employeeId")
                ?.toIntOrNull()

            if (employeeId == null) {

                EmployeeDetailsScreen(
                    employee = null,
                    onBack = {
                        navController.popBackStack()
                    }
                )

            } else {

                val employeeFlow = androidx.compose.runtime.remember(employeeId) {
                    viewModel.getEmployeeDetails(employeeId)
                }

                val employee by employeeFlow.collectAsStateWithLifecycle(
                    initialValue = null
                )

                EmployeeDetailsScreen(
                    employee = employee,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // Deleting an employee(s)
        composable(AppRoutes.DELETE_EMPLOYEE) {

            DeleteEmployeeScreen(
                employees = employees,

                onDeleteEmployee = { employee ->
                    viewModel.deleteEmployee(employee)
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Deleting a department(s)
        composable(AppRoutes.DELETE_DEPARTMENT) {

            DeleteDepartmentScreen(
                departments = departments,
                employees = employees,

                onDeleteDepartment = { department ->
                    viewModel.deleteDepartment(department)
                },

                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}