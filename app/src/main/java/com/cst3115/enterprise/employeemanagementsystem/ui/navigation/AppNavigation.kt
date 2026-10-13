package com.cst3115.enterprise.employeemanagementsystem.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier

import com.cst3115.enterprise.employeemanagementsystem.ui.screens.HomeScreen
import com.cst3115.enterprise.employeemanagementsystem.ui.screens.AddDepartmentScreen
import com.cst3115.enterprise.employeemanagementsystem.viewmodel.EmployeeViewModel

@Composable
fun AppNavigation(
    viewModel: EmployeeViewModel,
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

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
    }
}