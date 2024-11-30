package com.cardenas.diego.poketinder2024.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val inputsError = MutableLiveData<Boolean>()
    val authError = MutableLiveData<Boolean>()
    val loginSuccess = MutableLiveData<Boolean>()

    fun validateInputs(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            inputsError.value = true
            return
        }
        if (!isValidEmail(email)) {
            authError.value = true
            return
        }

        val storedEmail = "dracobelo23@gmail.com"
        val storedPassword = "Tecsup123"

        if (email != storedEmail || password != storedPassword) {
            authError.value = true
        } else {
            loginSuccess.value = true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$".toRegex()
        return email.matches(emailRegex)
    }
}
