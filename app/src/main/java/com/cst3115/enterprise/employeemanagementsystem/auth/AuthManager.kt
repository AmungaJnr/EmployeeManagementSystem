package com.cst3115.enterprise.employeemanagementsystem.auth

class AuthManager {

    fun validateCredentials(
        username: String,
        password: String
    ): Boolean {

        val validUsername = "admin"
        val validPassword = "Workforce@2026"

        return username.trim() == validUsername &&
                password == validPassword
    }
}