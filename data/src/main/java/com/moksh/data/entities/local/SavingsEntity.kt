package com.moksh.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "savings")
data class SavingsEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "target_amount")
    val targetAmount: Double,

    @ColumnInfo(name = "current_amount")
    val currentAmount: Double = 0.0,

    @ColumnInfo(name = "end_date")
    val endDate: Date? = null,

    @ColumnInfo(name = "progress_bar_color")
    val progressBarColor: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Date = Date(),

    @ColumnInfo(name = "is_active")
    val isActive: Boolean = true,

    @ColumnInfo(name = "notes")
    val notes: String? = null,

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false
)