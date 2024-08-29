package com.moksh.domain.model.response

data class User(
    val id: Long = 0,
    val name: String? = null,
    val email: String? = null,
    val phoneNumber: String
)
