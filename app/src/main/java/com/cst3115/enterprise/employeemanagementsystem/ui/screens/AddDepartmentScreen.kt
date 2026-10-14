package com.cst3115.enterprise.employeemanagementsystem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.cst3115.enterprise.employeemanagementsystem.ui.components.WorkforceScreenHeader
import com.cst3115.enterprise.employeemanagementsystem.ui.components.WorkforceFormSection

@Composable
fun AddDepartmentScreen(
    onSaveDepartment: (String) -> Unit,
    onBack: () -> Unit
) {
    // Creation of an observable value
    var departmentName by rememberSaveable {
        mutableStateOf("")
    }

    var isError by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        WorkforceScreenHeader(
            title = "Add Department",
            subtitle = "Create a new department for your organization.",
            onBack = onBack
        )

        WorkforceFormSection(
            title = "Department Information"
        ) {
            OutlinedTextField(
                value = departmentName,
                onValueChange = {
                    departmentName = it
                    isError = false
                },
                label = {
                    Text("Department Name")
                },
                placeholder = {
                    Text("e.g. Engineering")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = isError,
                supportingText = {
                    if (isError) {
                        Text("Department name cannot be empty")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )
        }


        Button(
            onClick = {

                val trimmedName = departmentName.trim()

                if (trimmedName.isEmpty()) {
                    isError = true
                } else {
                    onSaveDepartment(trimmedName)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Department")
        }

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}