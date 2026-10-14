package com.cst3115.enterprise.employeemanagementsystem.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onLogin: (String, String) -> Boolean
) {
    var username by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var errorMessage by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101E2B))
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Row(
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "WORKFORCE",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "™",
                fontSize = 12.sp,
                color = Color(0xFF42D9D2)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "THE THREE",
            fontSize = 14.sp,
            letterSpacing = 4.sp,
            color = Color(0xFF42D9D2)
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Welcome back",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sign in to manage your organization",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = ""
            },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color(0xFF42D9D2),
                unfocusedLabelColor = Color.LightGray,
                focusedBorderColor = Color(0xFF42D9D2),
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty(),
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Text(
                        text = if (passwordVisible) "Hide" else "Show",
                        color = Color(0xFF42D9D2)
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color(0xFF42D9D2),
                unfocusedLabelColor = Color.LightGray,
                focusedBorderColor = Color(0xFF42D9D2),
                unfocusedBorderColor = Color.Gray
            )
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = errorMessage,
                color = Color(0xFFFF8A80),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                errorMessage = when {
                    username.isBlank() ->
                        "Username is required."

                    password.isBlank() ->
                        "Password is required."

                    username.trim().length < 3 ->
                        "Username must contain at least 3 characters."

                    else -> {
                        if (onLogin(username.trim(), password)) {
                            ""
                        } else {
                            "Invalid username or password."
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Authorized personnel only",
            color = Color.LightGray,
            style = MaterialTheme.typography.labelSmall
        )
    }
}