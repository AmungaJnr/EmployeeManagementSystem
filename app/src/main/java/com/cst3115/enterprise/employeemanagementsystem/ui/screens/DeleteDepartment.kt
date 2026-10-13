package com.cst3115.enterprise.employeemanagementsystem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cst3115.enterprise.employeemanagementsystem.data.local.Department
import com.cst3115.enterprise.employeemanagementsystem.data.local.Employee

@Composable
fun DeleteDepartmentScreen(
    departments: List<Department>,
    employees: List<Employee>,
    onDeleteDepartment: (Department) -> Unit,
    onBack: () -> Unit
) {

    var selectedDepartment by remember {
        mutableStateOf<Department?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Text(
            text = "Delete Department",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Select a department to remove from your organization.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (departments.isEmpty()) {

            Text(
                text = "No departments available.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = departments,
                    key = { department -> department.id }
                ) { department ->

                    val employeeCount = employees.count {
                        it.deptID == department.id
                    }

                    OutlinedCard(
                        onClick = {
                            selectedDepartment = department
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Text(
                                text = department.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = "Department ID: ${department.id}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = "$employeeCount employee(s)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
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

    selectedDepartment?.let { department ->

        val affectedEmployees = employees.count {
            it.deptID == department.id
        }

        AlertDialog(
            onDismissRequest = {
                selectedDepartment = null
            },

            title = {
                Text("Delete Department?")
            },

            text = {
                Text(
                    "Are you sure you want to delete ${department.name}?\n\n" +
                            "This will also permanently delete " +
                            "$affectedEmployees employee(s) assigned to this department.\n\n" +
                            "This action cannot be undone."
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        onDeleteDepartment(department)
                        selectedDepartment = null
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
                        selectedDepartment = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}