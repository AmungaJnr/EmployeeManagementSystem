package com.cst3115.enterprise.employeemanagementsystem.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cst3115.enterprise.employeemanagementsystem.ui.components.EmployeeCard
import com.cst3115.enterprise.employeemanagementsystem.ui.components.StatCard
import com.cst3115.enterprise.employeemanagementsystem.viewmodel.EmployeeViewModel

@Composable
fun HomeScreen(
    viewModel: EmployeeViewModel,
    onAddDepartment: () -> Unit,
    onAddEmployee: () -> Unit,
    onEmployeeClick: (Int) -> Unit,
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAddDepartment,
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Add Department")
        }

        Spacer(modifier = Modifier.height(9.dp))

        Button(
            onClick = onAddEmployee,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Employee")
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
                    deptID = employee.deptID,
                    onClick = {
                        onEmployeeClick(employee.id)
                    }
                )
            }
        }
    }
}