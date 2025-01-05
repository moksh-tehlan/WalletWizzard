package com.moksh.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moksh.data.dao.CategoryDao
import com.moksh.data.dao.PaymentModeDao
import com.moksh.data.dao.SavingDao
import com.moksh.data.dao.TransactionDao
import com.moksh.data.dao.UserDao
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.data.entities.local.PaymentModeEntity
import com.moksh.data.entities.local.SavingsEntity
import com.moksh.data.entities.local.TransactionEntity
import com.moksh.data.entities.local.UserEntity
import com.moksh.data.entities.utils.Converters
import timber.log.Timber
import java.util.concurrent.Executors


@Database(
    entities = [
        TransactionEntity::class,
        UserEntity::class,
        CategoryEntity::class,
        PaymentModeEntity::class,
        SavingsEntity::class,
    ], version = 8
)
@TypeConverters(Converters::class)
abstract class WizzardDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun paymentModeDao(): PaymentModeDao
    abstract fun savingsDao(): SavingDao

    companion object {
        private const val DATABASE_NAME = "wizzard_database.db"
        fun getRoomDatabase(context: Context): WizzardDatabase {
            val appContext = context.applicationContext
            val dbFile = appContext.getDatabasePath(DATABASE_NAME)
            val builder = Room
                .databaseBuilder(
                    context = appContext,
                    klass = WizzardDatabase::class.java,
                    name = dbFile.absolutePath
                )
                .fallbackToDestructiveMigration()
                .setJournalMode(JournalMode.TRUNCATE)
                .apply {
                    setQueryCallback({ sqlQuery, bindArgs ->
                        Timber.tag("RoomDatabase")
                            .d("%s%s", "SQL Query: $sqlQuery SQL Args: ", bindArgs)
                    }, Executors.newSingleThreadExecutor())
                }
                .build()
            return builder
        }
    }
}