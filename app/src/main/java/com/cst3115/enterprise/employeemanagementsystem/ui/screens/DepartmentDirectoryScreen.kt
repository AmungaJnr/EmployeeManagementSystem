package com.cst3115.enterprise.employeemanagementsystem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.cst3115.enterprise.employeemanagementsystem.data.local.Department
import com.cst3115.enterprise.employeemanagementsystem.data.local.Employee
import com.cst3115.enterprise.employeemanagementsystem.ui.components.WorkforceScreenHeader

@Composable
fun DepartmentDirectoryScreen(
    departments: List<Department>,
    employees: List<Employee>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            WorkforceScreenHeader(
                title = "Department Directory",
                subtitle = "Explore your organization.",
                onBack = onBack
            )
        }

        item {
            Text(
                text = "${departments.size} Departments",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (departments.isEmpty()) {

            item {
                Text(
                    text = "No departments have been created yet.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

        } else {

            items(
                items = departments,
                key = { it.id }
            ) { department ->

                val employeeCount = employees.count {
                    it.deptID == department.id
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = department.name,
                            style = MaterialTheme.typography.titleLarge
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
                    }
                }
            }
        }
    }
}