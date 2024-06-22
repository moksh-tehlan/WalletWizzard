package com.moksh.data.repository

import com.moksh.domain.repository.MokshRepository
import javax.inject.Inject

class MokshRepositoryImpl @Inject constructor() : MokshRepository {
    override fun helloMoksh(): String {
        return "Hello Moksh"
    }
}