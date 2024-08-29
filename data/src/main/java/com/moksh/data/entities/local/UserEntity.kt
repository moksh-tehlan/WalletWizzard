package com.moksh.data.entities.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.moksh.domain.model.response.User

@Entity(
    tableName = "user"
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String? = null,
    val email: String? = null,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
) {
    companion object {
        fun fromUser(user: User): UserEntity {
            return UserEntity(
                id = user.id,
                name = user.name,
                email = user.email,
                phoneNumber = user.phoneNumber
            )
        }
    }
}
