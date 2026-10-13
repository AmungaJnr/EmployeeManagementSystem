package com.cst3115.enterprise.employeemanagementsystem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import com.cst3115.enterprise.employeemanagementsystem.data.local.Department
import com.cst3115.enterprise.employeemanagementsystem.util.generateEmployeeEmail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEmployeeScreen(
    departments: List<Department>,
    onSaveEmployee: (String, String, Int) -> Unit,
    onBack: () -> Unit
) {

    var employeeName by rememberSaveable {
        mutableStateOf("")
    }

    val employeeEmail = generateEmployeeEmail(employeeName)

    var selectedDeptID by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    var departmentExpanded by remember {
        mutableStateOf(false)
    }

    var showError by rememberSaveable {
        mutableStateOf(false)
    }

    val selectedDepartment = departments.find {
        it.id == selectedDeptID
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Add Employee",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Register a new team member.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        OutlinedTextField(
            value = employeeName,
            onValueChange = {
                employeeName = it
                showError = false
            },
            label = { Text("Full Name") },
            placeholder = { Text("e.g. John Doe") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = showError && employeeName.isBlank()
        )

        OutlinedTextField(
            value = if (employeeName.isBlank()) {
                ""
            } else {
                employeeEmail
            },
            onValueChange = {},
            label = {
                Text("Company Email")
            },
            placeholder = {
                Text("Automatically generated")
            },
            supportingText = {
                Text("Generated from the employee's name")
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            singleLine = true
        )

        ExposedDropdownMenuBox(
            expanded = departmentExpanded,
            onExpandedChange = {
                departmentExpanded = it
            }
        ) {

            OutlinedTextField(
                value = selectedDepartment?.let {
                    "${it.name} · ID ${it.id}"
                } ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Department") },
                placeholder = { Text("Select a department") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = departmentExpanded
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(
                        ExposedDropdownMenuAnchorType.PrimaryNotEditable
                    ),
                isError = showError && selectedDepartment == null
            )

            ExposedDropdownMenu(
                expanded = departmentExpanded,
                onDismissRequest = {
                    departmentExpanded = false
                }
            ) {

                departments.forEach { department ->

                    DropdownMenuItem(
                        text = {
                            Text(
                                "${department.name} · ID ${department.id}"
                            )
                        },
                        onClick = {
                            selectedDeptID = department.id
                            departmentExpanded = false
                            showError = false
                        }
                    )
                }
            }
        }

        if (departments.isEmpty()) {
            Text(
                text = "No departments available. Add a department first.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (showError) {
            Text(
                text = "Enter an employee name, and select a department.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {

                val name = employeeName.trim()

                if (
                    name.isNotEmpty() &&
                    selectedDepartment != null
                ) {

                    val email = generateEmployeeEmail(name)

                    onSaveEmployee(
                        name,
                        email,
                        selectedDepartment.id
                    )

                } else {
                    showError = true
                }
            },
            enabled = departments.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Employee")
        }

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}