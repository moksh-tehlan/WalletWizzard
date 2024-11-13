package com.moksh.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moksh.domain.model.response.TransactionType
import java.util.Date
import java.util.UUID

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "icon") val icon: String? = null,
    @ColumnInfo(name = "color") val color: String? = null,
    @ColumnInfo(name = "type") val type: TransactionType,
    @ColumnInfo(name = "is_synced") val isSynced: Boolean? = false,
    @ColumnInfo(name = "created_at") val createdAt: Date? = Date(),
    @ColumnInfo(name = "updated_at") val updatedAt: Date? = Date()
)