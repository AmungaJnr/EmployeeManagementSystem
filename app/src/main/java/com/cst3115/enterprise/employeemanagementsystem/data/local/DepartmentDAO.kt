package com.cst3115.enterprise.employeemanagementsystem.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface DepartmentDAO {
    @Insert
    suspend fun insertDepartment(department: Department)

    @Delete
    suspend fun deleteDepartment(department: Department)

    @Query("SELECT * FROM departments")
    fun getAllDepartments(): Flow<List<Department>> // flow is used to make the display reactive.
}