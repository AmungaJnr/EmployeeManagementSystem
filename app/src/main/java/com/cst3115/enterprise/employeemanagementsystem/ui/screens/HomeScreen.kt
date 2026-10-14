package com.cst3115.enterprise.employeemanagementsystem.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    viewModel: EmployeeViewModel,
    onAddDepartment: () -> Unit,
    onAddEmployee: () -> Unit,
    onEmployeeClick: (Int) -> Unit,
    onDeleteEmployee: () -> Unit,
    onDeleteDepartment: () -> Unit,
    modifier: Modifier = Modifier ) {

    val employees by viewModel.employees.collectAsStateWithLifecycle()

    val departments by viewModel.departments.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        contentPadding = PaddingValues(
            start = 20.dp,
            end = 20.dp,
            top = 24.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // 1. BRAND HEADER
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Row {
                    Text(
                        text = "WORKFORCE",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "™",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "THE THREE",
                    style = MaterialTheme.typography.labelMedium,
                    letterSpacing = 4.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 2. DASHBOARD OVERVIEW
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Text(
                    text = "Workforce Overview",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Your organization at a glance",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    StatCard(
                        title = "Employees",
                        value = employees.size.toString(),
                        description = "Total workforce",
                        modifier = Modifier.weight(1f)
                    )

                    StatCard(
                        title = "Departments",
                        value = departments.size.toString(),
                        description = "Departments",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 3. QUICK ACTIONS
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "QUICK ACTIONS",
                    style = MaterialTheme.typography.labelLarge,
                    letterSpacing = 2.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Button(
                        onClick = onAddEmployee,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(
                            horizontal = 8.dp,
                            vertical = 14.dp
                        )
                    ) {
                        Text("+ Employee")
                    }

                    Button(
                        onClick = onAddDepartment,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(
                            horizontal = 8.dp,
                            vertical = 14.dp
                        )
                    ) {
                        Text("+ Department")
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    OutlinedButton(
                        onClick = onDeleteEmployee,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(
                            horizontal = 8.dp,
                            vertical = 14.dp
                        )
                    ) {
                        Text("− Employee")
                    }

                    OutlinedButton(
                        onClick = onDeleteDepartment,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(
                            horizontal = 8.dp,
                            vertical = 14.dp
                        )
                    ) {
                        Text("− Department")
                    }
                }
            }
        }

        // 4. EMPLOYEE DIRECTORY HEADING
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Employee Directory",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Browse your organization",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 5. EMPLOYEE RECORDS
        if (employees.isEmpty()) {

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = "No employees yet",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Add your first employee to start building your directory.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

        } else {

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