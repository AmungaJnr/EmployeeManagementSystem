package com.cst3115.enterprise.employeemanagementsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.cst3115.enterprise.employeemanagementsystem.ui.theme.EmployeeManagementSystemTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cst3115.enterprise.employeemanagementsystem.data.local.AppDatabase
import com.cst3115.enterprise.employeemanagementsystem.data.repository.EmployeeRepository
import com.cst3115.enterprise.employeemanagementsystem.viewmodel.EmployeeViewModel
import com.cst3115.enterprise.employeemanagementsystem.viewmodel.EmployeeViewModelFactory
import com.cst3115.enterprise.employeemanagementsystem.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)

        val repository = EmployeeRepository(
            employeeDao = database.employeeDao(),
            departmentDao = database.departmentDao()
        )

        setContent {

            val employeeViewModel: EmployeeViewModel = viewModel(
                factory = EmployeeViewModelFactory(repository)
            )

            EmployeeManagementSystemTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->

                    AppNavigation(
                        viewModel = employeeViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    EmployeeManagementSystemTheme {
        HomeScreen()
    }
}

// Preview both themes without changing emulator.
@Preview(
    name = "Light Theme",
    showBackground = true
)
@Composable
fun HomeScreenLightPreview(){
    EmployeeManagementSystemTheme(darkTheme = false) {
        HomeScreen()
    }
}

@Preview(
    name = "Dark Theme",
    showBackground = true
)

@Composable
fun HomeScreenDarkPreview(){
    EmployeeManagementSystemTheme(darkTheme = true) {
        HomeScreen()
    }

}

 */