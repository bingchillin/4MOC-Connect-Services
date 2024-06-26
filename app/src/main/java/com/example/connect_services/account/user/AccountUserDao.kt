package com.example.connect_services.account.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AccountUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg accountUser: AccountUser)

    @Insert
    fun insertAll(vararg accountUser: AccountUser)

    @Update
    fun update(accountUser: AccountUser)

    @Delete
    fun delete(accountUser: AccountUser)

    @Query("SELECT * FROM AccountUser")
    fun getAll(): List<AccountUser>

    @Query("SELECT * FROM AccountUser WHERE Service=:service LIMIT 1")
    fun get(service: String): AccountUser?



}