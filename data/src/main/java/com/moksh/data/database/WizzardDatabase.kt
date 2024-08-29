package com.moksh.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moksh.data.dto.CategoryDao
import com.moksh.data.dto.ExpenseDao
import com.moksh.data.dto.IncomeDao
import com.moksh.data.dto.UserDao
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.data.entities.local.ExpenseEntity
import com.moksh.data.entities.local.IncomeEntity
import com.moksh.data.entities.local.UserEntity
import com.moksh.data.entities.utils.Converters


@Database(
    entities = [
        IncomeEntity::class,
        ExpenseEntity::class,
        UserEntity::class,
        CategoryEntity::class
    ], version = 5
)
@TypeConverters(Converters::class)
abstract class WizzardDatabase : RoomDatabase() {
    abstract fun incomeDao(): IncomeDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        private const val DATABASE_NAME = "wizzard_database.db"
        fun getRoomDatabase(context: Context): WizzardDatabase {
            val appContext = context.applicationContext
            val dbFile = appContext.getDatabasePath(DATABASE_NAME)
            val builder = Room
                .databaseBuilder(
                    context = appContext,
                    klass = WizzardDatabase::class.java,
                    name = dbFile.absolutePath
                )
                .fallbackToDestructiveMigration()
                .build()
            return builder
        }
    }
}