package com.example.connect_services

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MyFormViewModel: ViewModel() {
    private val _serviceValue = mutableStateOf("")
    val serviceValue: String = _serviceValue.value

    private val _identityValue = mutableStateOf("")
    val identityValue: String = _identityValue.value

    private val _passwordValue = mutableStateOf("")
    val passwordValue: String = _passwordValue.value

//    val name: LiveData<State<String>> = mutableStateOf("")// MutableLiveData()



}