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
import androidx.compose.ui.platform.LocalContext
import com.cst3115.enterprise.employeemanagementsystem.auth.AuthManager
import com.cst3115.enterprise.employeemanagementsystem.auth.SessionManager

import com.cst3115.enterprise.employeemanagementsystem.ui.screens.HomeScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.AddDepartmentScreen
import com.cst3115.enterprise.employeemanagementsystem.viewmodel.EmployeeViewModel
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.AddEmployeeScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.EmployeeDetailsScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.DeleteEmployeeScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.DeleteDepartmentScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.SplashScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.DepartmentDirectoryScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.LoginScreen
import com.cst3115.enterprise.employeemanagementsystem.auth.AuthGuard

@Composable
fun AppNavigation(
    viewModel: EmployeeViewModel,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    val departments by viewModel.departments.collectAsStateWithLifecycle()

    val employees by viewModel.employees.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val authManager = remember{
        AuthManager()
    }

    val sessionManager = remember(context) {
        SessionManager(context)
    }

    val onUnauthorized = {
        navController.navigate(AppRoutes.LOGIN) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppRoutes.SPLASH,
        modifier = modifier
    ) {

        // Splash Screen
        composable(AppRoutes.SPLASH) {

            SplashScreen(
                onSplashFinished = {

                    val destination = if(sessionManager.isLoggedIn()){
                        AppRoutes.HOME
                    } else {
                        AppRoutes.LOGIN
                    }

                    navController.navigate(destination) {

                        popUpTo(AppRoutes.SPLASH) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        // Login Screen
        composable(AppRoutes.LOGIN) {

            LoginScreen(
                onLogin = { username, password ->

                    val authenticated = authManager.validateCredentials(
                        username,
                        password
                    )

                    if (authenticated) {

                        sessionManager.saveSession()

                        navController.navigate(AppRoutes.HOME) {
                            popUpTo(AppRoutes.LOGIN) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }

                    authenticated
                }
            )
        }

        // Home Screen
        composable(AppRoutes.HOME) {

            AuthGuard(
                sessionManager = sessionManager,
                onUnauthorized = onUnauthorized
            ) {

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
                    },

                    onViewDepartments = {
                        navController.navigate(AppRoutes.DEPARTMENT_DIRECTORY)
                    },

                    onLogout = {
                        sessionManager.logout()

                        navController.navigate(AppRoutes.LOGIN) {
                            popUpTo(AppRoutes.HOME) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }


        }

        // Add department
        composable(AppRoutes.ADD_DEPARTMENT) {

            AuthGuard(
                sessionManager = sessionManager,
                onUnauthorized = onUnauthorized
            ){

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

        }

        // Add employee
        composable(AppRoutes.ADD_EMPLOYEE){

            AuthGuard(
                sessionManager = sessionManager,
                onUnauthorized = onUnauthorized
            ){
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

        }

        // Employee Details
        composable(AppRoutes.EMPLOYEE_DETAILS) { backStackEntry ->

            val employeeId = backStackEntry.arguments
                ?.getString("employeeId")
                ?.toIntOrNull()

            AuthGuard(
                sessionManager = sessionManager,
                onUnauthorized = onUnauthorized
            ){

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

        }

        // Deleting an employee(s)
        composable(AppRoutes.DELETE_EMPLOYEE) {

            AuthGuard(
                sessionManager = sessionManager,
                onUnauthorized = onUnauthorized
            ){
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

        }

        // Deleting a department(s)
        composable(AppRoutes.DELETE_DEPARTMENT) {

            AuthGuard(
                sessionManager = sessionManager,
                onUnauthorized = onUnauthorized
            ){
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

        // Department directory
        composable(AppRoutes.DEPARTMENT_DIRECTORY) {

            AuthGuard(
                sessionManager = sessionManager,
                onUnauthorized = onUnauthorized
            ){
                DepartmentDirectoryScreen(
                    departments = departments,
                    employees = employees,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

        }
    }
}