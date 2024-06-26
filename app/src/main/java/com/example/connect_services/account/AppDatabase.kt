package com.example.connect_services.account

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.connect_services.account.user.AccountUser
import com.example.connect_services.account.user.AccountUserDao

@Database(entities = [AccountUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountUserDao(): AccountUserDao
}
