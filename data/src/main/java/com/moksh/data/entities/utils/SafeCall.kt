package com.moksh.data.entities.utils

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import java.net.ConnectException
import java.net.SocketTimeoutException

inline fun <reified T> safeCall(
    call: () -> T
): Result<T, DataError> {
    val response = try {
        call()
    } catch (e: Exception) {
        return when (e) {
            is SocketTimeoutException -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
            is ConnectException, is FirebaseNetworkException -> Result.Error(DataError.Network.NO_INTERNET)
            is FirebaseTooManyRequestsException -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)

            // Database Errors
            is SQLiteConstraintException -> Result.Error(DataError.Local.DUPLICATE_DATA)
            is SQLiteFullException -> Result.Error(DataError.Local.DATABASE_FULL)
            is SQLiteException -> Result.Error(DataError.Local.SQL_ERROR)

            // Unknown Errors
            else -> Result.Error(DataError.Local.UNKNOWN)
        }.also { e.printStackTrace() }
    }
    return Result.Success(response)
}

/*
inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Network> {
    return when (response.status.value) {
        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}*/
