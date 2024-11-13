package com.moksh.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.moksh.domain.model.response.User
import java.util.UUID

@Entity(
    tableName = "user"
)
data class UserEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String? = null,
    val email: String? = null,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
)
