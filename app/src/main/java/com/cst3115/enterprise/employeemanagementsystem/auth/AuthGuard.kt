package com.cst3115.enterprise.employeemanagementsystem.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun AuthGuard(
    sessionManager: SessionManager,
    onUnauthorized: () -> Unit,
    content: @Composable () -> Unit
) {
    if (sessionManager.isLoggedIn()) {
        content()
    } else {
        LaunchedEffect(Unit) {
            onUnauthorized()
        }
    }
}