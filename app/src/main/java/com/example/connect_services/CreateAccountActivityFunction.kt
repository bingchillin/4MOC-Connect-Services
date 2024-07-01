package com.example.connect_services

import android.content.Context
import androidx.room.Room
import com.example.connect_services.account.AppDatabase
import com.example.connect_services.account.user.AccountUser
import com.example.connect_services.services.ServiceAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountActivityFunction {


    fun onSaveButtonClick(
        context: Context,
        serviceValue: String,
        identityValue: String,
        passwordValue: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val serviceAPI = ServiceAPI()

            // Fields Validation
            val serviceError = serviceValue.isEmpty()
            val identityError = identityValue.isEmpty()
            val passwordError = passwordValue.isEmpty()

            if (serviceError || identityError || passwordError) {
                onFailure.invoke()
                return@launch
            }

            val db = Room.databaseBuilder(context, AppDatabase::class.java, "MyAccount.db").build()
            val accountUserDao = db.accountUserDao()

            val checkUserAccountExist = serviceAPI.getAccountUserServiceId(accountUserDao, serviceValue, identityValue)

            if (!checkUserAccountExist) {
                val accountCreate = AccountUser(
                    service = serviceValue,
                    idService = identityValue,
                    password = passwordValue
                )

                serviceAPI.insertAccountUser(accountUserDao, accountCreate)

                onSuccess.invoke()
            } else {
                onFailure.invoke()
            }
        }
    }

}