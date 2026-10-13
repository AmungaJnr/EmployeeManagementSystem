package com.cst3115.enterprise.employeemanagementsystem.util

fun generateEmployeeEmail(name: String): String {

    val nameParts = name
        .trim()
        .lowercase()
        .split(Regex("\\s+"))
        .filter { it.isNotEmpty() }

    val emailName = nameParts.joinToString(".")

    return "$emailName@thethree.com"
}