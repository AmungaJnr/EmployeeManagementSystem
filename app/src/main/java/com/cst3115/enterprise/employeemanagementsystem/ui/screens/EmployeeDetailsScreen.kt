package com.cst3115.enterprise.employeemanagementsystem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import com.cst3115.enterprise.employeemanagementsystem.data.local.EmployeeDetails
import com.cst3115.enterprise.employeemanagementsystem.ui.components.EmployeeAvatar
import com.cst3115.enterprise.employeemanagementsystem.ui.components.getInitials
import com.cst3115.enterprise.employeemanagementsystem.ui.components.WorkforceScreenHeader
import com.cst3115.enterprise.employeemanagementsystem.ui.components.WorkforceFormSection

@Composable
fun EmployeeDetailsScreen(
    employee: EmployeeDetails?,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // REUSABLE SCREEN HEADER
        WorkforceScreenHeader(
            title = "Employee Details",
            subtitle = "View employee information and department assignment.",
            onBack = onBack
        )

        if (employee == null) {

            Text(
                text = "Employee information is unavailable.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        } else {

            // EMPLOYEE PROFILE
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                EmployeeAvatar(
                    initials = getInitials(employee.name)
                )

                Text(
                    text = employee.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Employee #${employee.id}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // PERSONAL INFORMATION CARD
            WorkforceFormSection(
                title = "Personal Information"
            ) {

                Text(
                    text = "Company Email",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = employee.email,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // DEPARTMENT INFORMATION CARD
            WorkforceFormSection(
                title = "Department Information"
            ) {

                Text(
                    text = employee.departmentName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "Department ID: ${employee.deptID}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}