package com.cst3115.enterprise.employeemanagementsystem.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "employees",

    foreignKeys = [
        ForeignKey(
            entity = Department::class,
            parentColumns = ["id"],
            childColumns = ["deptID"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [Index(value = ["deptID"])] // database optimization; helps filter quicker
)

data class Employee(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val email: String,
    val deptID: Int
)