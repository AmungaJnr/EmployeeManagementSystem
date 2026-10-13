package com.cst3115.enterprise.employeemanagementsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cst3115.enterprise.employeemanagementsystem.ui.theme.EmployeeManagementSystemTheme
import com.cst3115.enterprise.employeemanagementsystem.ui.theme.components.StatCard
import androidx.compose.foundation.lazy.items
import com.cst3115.enterprise.employeemanagementsystem.ui.theme.components.EmployeeCard
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cst3115.enterprise.employeemanagementsystem.data.local.AppDatabase
import com.cst3115.enterprise.employeemanagementsystem.data.repository.EmployeeRepository
import com.cst3115.enterprise.employeemanagementsystem.viewmodel.EmployeeViewModel
import com.cst3115.enterprise.employeemanagementsystem.viewmodel.EmployeeViewModelFactory
import androidx.compose.runtime.getValue


/* Test data before Room
data class EmployeePreview(
    val id: Int,
    val name: String,
    val email: String,
    val deptId: Int
)
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val database = AppDatabase.getDatabase(applicationContext)

            val repository = EmployeeRepository(
                employeeDao = database.employeeDao(),
                departmentDao = database.departmentDao()
            )

            val employeeViewModel: EmployeeViewModel = viewModel(
                factory = EmployeeViewModelFactory(repository)
            )

            EmployeeManagementSystemTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->

                    HomeScreen(
                        viewModel = employeeViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    viewModel: EmployeeViewModel,
    modifier: Modifier = Modifier ) {

    val employees by viewModel.employees.collectAsStateWithLifecycle()

    val departments by viewModel.departments.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ){

        Text(
            text = "WORKFORCE",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "People & Operations",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Your workforce at a glance",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ){
            StatCard(
                title = "EMPLOYEES",
                value = employees.size.toString(),
                description = "Total workforce",
                modifier = Modifier.weight(1f)
            )

            StatCard(
                title = "DEPARTMENTS",
                value = departments.size.toString(),
                description = "Business units",
                modifier = Modifier.weight(1f)
           )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Employee Directory",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = employees,
                key = { employee -> employee.id }
            ) { employee ->

                EmployeeCard(
                    name = employee.name,
                    email = employee.email,
                    deptID = employee.deptID
                )
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