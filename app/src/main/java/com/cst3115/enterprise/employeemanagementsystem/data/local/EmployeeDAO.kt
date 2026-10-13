package com.cst3115.enterprise.employeemanagementsystem.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDAO {
    @Insert
    suspend fun insertEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employees ORDER BY name ASC")
    fun getAllEmployees(): Flow<List<Employee>>

    @Query("SELECT * FROM employees WHERE id = :employeeId")
    fun getEmployeeById(employeeId: Int): Flow<Employee?>

    @Query(
        """
    SELECT
        employees.id,
        employees.name,
        employees.email,
        employees.deptID,
        departments.name AS departmentName
        
    FROM employees
    
    INNER JOIN departments
        ON employees.deptID = departments.id
        
    WHERE employees.id = :employeeId
    """
    )
    fun getEmployeeDetails(employeeId: Int): Flow<EmployeeDetails?>
}