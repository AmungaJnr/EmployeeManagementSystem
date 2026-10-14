package com.cst3115.enterprise.employeemanagementsystem.auth

import android.content.Context

class SessionManager(context: Context) {
    private val preferences = context.applicationContext
        .getSharedPreferences(
            "workforce_session",
            Context.MODE_PRIVATE
        )

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean(
            "is_logged_in",
            false
        )
    }

    fun saveSession() {
        preferences.edit()
            .putBoolean("is_logged_in", true)
            .apply()
    }

    fun logout() {
        preferences.edit()
            .remove("is_logged_in")
            .apply()
    }
}