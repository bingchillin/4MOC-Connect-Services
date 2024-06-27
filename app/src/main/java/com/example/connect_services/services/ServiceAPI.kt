package com.example.connect_services.services

import androidx.room.Room
import com.example.connect_services.account.AppDatabase
import com.example.connect_services.account.user.AccountUser
import com.example.connect_services.account.user.AccountUserDao

class ServiceAPI {


    fun insertAccountUser(accountUserDao: AccountUserDao, accountUser: AccountUser) {
        accountUserDao.insert(AccountUser( service = accountUser.service, idService = accountUser.idService, password = accountUser.password))
    }

    fun getAccountUserService(accountUserDao: AccountUserDao, service: String): AccountUser? {
        return accountUserDao.get(service)
    }

    fun getAccountUser(accountUserDao: AccountUserDao): List<AccountUser>? {
        return accountUserDao.getAll()
    }

    fun getAccountUserServiceId(accountUserDao: AccountUserDao, service: String, identifiant: String): Boolean {
        val accountUser = accountUserDao.getUserServiceId(service, identifiant)
        return accountUser != null
    }



}

