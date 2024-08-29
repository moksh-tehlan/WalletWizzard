package com.moksh.data.entities.utils

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteFullException
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result

inline fun <reified T> safeDbCall(call: () -> T): Result<T, DataError.Local> {
    val response = try {
        call()
    } catch (e: SQLiteConstraintException) {
        e.printStackTrace()
        return Result.Error(DataError.Local.DUPLICATE_DATA)
    } catch (e: SQLiteFullException) {
        e.printStackTrace()
        return Result.Error(DataError.Local.DATABASE_FULL)
    } catch (e: SQLiteException) {
        e.printStackTrace()
        return Result.Error(DataError.Local.SQL_ERROR)
    } catch (e: Exception) {
        e.printStackTrace()
        return Result.Error(DataError.Local.UNKNOWN)
    }

    return Result.Success(response)
}