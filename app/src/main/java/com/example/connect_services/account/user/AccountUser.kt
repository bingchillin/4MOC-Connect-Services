package com.example.connect_services.account.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountUser(
    @PrimaryKey(autoGenerate = true) val auid: Long = 0,
    @ColumnInfo("Service") val service: String,
    @ColumnInfo("Identifiant") val idService: String,
    @ColumnInfo("Password") val password: String
)
