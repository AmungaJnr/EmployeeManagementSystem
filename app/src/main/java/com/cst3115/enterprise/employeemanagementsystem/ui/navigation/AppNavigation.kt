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

@Composable
fun AppNavigation(
    viewModel: EmployeeViewModel,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val departments by viewModel.departments.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME,
        modifier = modifier
    ) {

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
                }
            )
        }

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
    }
}