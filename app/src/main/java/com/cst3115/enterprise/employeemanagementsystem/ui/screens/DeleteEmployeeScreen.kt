package com.cst3115.enterprise.employeemanagementsystem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cst3115.enterprise.employeemanagementsystem.data.local.Employee

@Composable
fun DeleteEmployeeScreen(
    employees: List<Employee>,
    onDeleteEmployee: (Employee) -> Unit,
    onBack: () -> Unit
) {

    var selectedEmployee by remember {
        mutableStateOf<Employee?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TextButton(onClick = onBack) {
            Text("← Back")
        }

        Text(
            text = "Delete Employee",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Select an employee to remove from your organization.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (employees.isEmpty()) {

            Text(
                text = "No employees available.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        } else {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {

                items(
                    items = employees,
                    key = { it.id }
                ) { employee ->

                    OutlinedCard(
                        onClick = {
                            selectedEmployee = employee
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {

                            Text(
                                text = employee.name,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Text(
                                text = employee.email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = "Tap to delete",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }

    if (selectedEmployee != null) {

        AlertDialog(
            onDismissRequest = {
                selectedEmployee = null
            },

            title = {
                Text("Delete Employee?")
            },

            text = {
                Text(
                    "Are you sure you want to delete " +
                            "${selectedEmployee?.name}? " +
                            "This action cannot be undone."
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {

                        selectedEmployee?.let { employee ->
                            onDeleteEmployee(employee)
                        }

                        selectedEmployee = null
                    }
                ) {
                    Text(
                        text = "Delete",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },

            dismissButton = {

                TextButton(
                    onClick = {
                        selectedEmployee = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}