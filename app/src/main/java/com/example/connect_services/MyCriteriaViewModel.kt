package com.example.connect_services

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyCriteriaViewModel: ViewModel() {

    private val _lengthValue = mutableStateOf("")

    var selectedOption: Int by mutableIntStateOf(1)
    var digitPwd: Boolean by mutableStateOf(true)

    var specialChar: Boolean by mutableStateOf(false)

    val lengthValue: Int
        get() = _lengthValue.value.toIntOrNull() ?: 8 // default 8


    var lengthStringValue: String
        get() = _lengthValue.value
        set(value) {
            _lengthValue.value = value
        }

    fun generatePassword(): String {
        val length = lengthStringValue.toIntOrNull() ?: 8
        val upperChars = ('A'..'Z').toList()
        val lowerChars = ('a'..'z').toList()
        val digits = ('0'..'9').toList()
        val specialChars = listOf('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '-', '+', '=')

        var allowedChars = when (selectedOption) {
            1 -> upperChars
            2 -> lowerChars
            3 -> upperChars + lowerChars
            else -> upperChars
        }

        if (digitPwd) {
            allowedChars += digits
        }

        if (specialChar) {
            allowedChars += specialChars
        }

        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}