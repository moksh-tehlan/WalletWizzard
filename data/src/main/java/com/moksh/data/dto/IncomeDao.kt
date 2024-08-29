package com.moksh.data.dto

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.moksh.data.entities.local.IncomeEntity
import com.moksh.data.entities.local.IncomeWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Query("SELECT * FROM income")
    fun getIncome(): Flow<List<IncomeWithCategory>>

    @Query("SELECT * FROM income WHERE id = :id")
    suspend fun getIncomeById(id: Long): IncomeEntity

    @Insert
    suspend fun insertIncome(income: IncomeEntity): Long

    @Update
    suspend fun updateIncome(income: IncomeEntity)

    @Query("DELETE FROM income WHERE id = :id")
    suspend fun deleteIncome(id: Long)
}