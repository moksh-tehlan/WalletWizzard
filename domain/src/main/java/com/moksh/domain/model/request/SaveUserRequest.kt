package com.moksh.domain.model.request

data class SaveUserRequest(
    val phoneNumber: String,
    val name: String? = null,
    val email: String? = null,
)