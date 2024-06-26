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

    fun getAccountUserByAuid(accountUserDao: AccountUserDao, auid: Long): AccountUser? {
        return accountUserDao.getUserByAuid(auid)
    }

    fun updateAccountUserService(accountUserDao: AccountUserDao, auid: Long, identifiant: String, password: String, service: String) {
        accountUserDao.updateUser(auid, identifiant, password, service)
        println(auid)
        println(service)
        println(identifiant)
        println(password)
    }

    fun deleteAccountUserService(accountUserDao: AccountUserDao, auid: Long) {
        accountUserDao.deleteUserByAuid(auid)
    }

}

