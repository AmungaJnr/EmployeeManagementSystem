package com.cst3115.enterprise.employeemanagementsystem.data.local
/*
Responsible for defining and providing access to the database.
 */

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Employee::class, Department::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDAO

    abstract fun departmentDao(): DepartmentDAO

    // Singleton pattern; allows the application to maintain one shared Room database instance.
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "workforce_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}