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

    @Query("SELECT * FROM AccountUser WHERE Identifiant=:identifiant LIMIT 1")
    fun get(identifiant: String): AccountUser?

    @Query("SELECT * FROM AccountUser WHERE Service=:service AND Identifiant=:identifiant LIMIT 1")
    fun getUserServiceId(service: String, identifiant: String): AccountUser?

    @Query("SELECT * FROM AccountUser WHERE auid=:auid LIMIT 1")
    fun getUserByAuid(auid: Long): AccountUser?

    @Query("UPDATE AccountUser SET Identifiant=:identifiant, Password=:password, Service=:service WHERE auid=:auid")
    fun updateUser(auid: Long, identifiant: String, password: String, service: String)

}