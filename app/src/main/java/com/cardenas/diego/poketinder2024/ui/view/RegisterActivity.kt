package com.cardenas.diego.poketinder2024.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.Log
import com.cardenas.diego.poketinder2024.data.database.SharedPreferencesRepository
import com.cardenas.diego.poketinder2024.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtPassword2: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var btnBackClose: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        edtPassword2 = findViewById(R.id.edtPassword2)
        btnRegister = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnLogin)
        btnBackClose = findViewById(R.id.btnBackClose)

        btnRegister.setOnClickListener { registerUser() }
        btnLogin.setOnClickListener { loginUser() }
        btnBackClose.setOnClickListener { onBackPressed() }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validateInputs(): Boolean {
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val password2 = edtPassword2.text.toString().trim()

        if (!isValidEmail(email)) {
            edtEmail.error = "Correo electrónico inválido"
            return false
        }
        if (password.length < 8) {
            edtPassword.error = "La contraseña debe tener al menos 8 caracteres"
            return false
        }
        if (password != password2) {
            edtPassword2.error = "Las contraseñas no coinciden"
            return false
        }
        return true
    }

    private fun registerUser() {
        if (validateInputs()) {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("email", email)
                putString("password", password)
                apply()
            }

            Log.d("RegisterActivity", "Correo guardado: $email, Contraseña guardada: $password")

            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }

    private fun loginUser() {
        val emailInput = edtEmail.text.toString().trim()
        val passwordInput = edtPassword.text.toString().trim()

        val sharedPreferencesRepository = SharedPreferencesRepository()
        sharedPreferencesRepository.setSharedPreference(this)

        val savedEmail = sharedPreferencesRepository.getUserEmail()
        val savedPassword = sharedPreferencesRepository.getUserPassword()

        Log.d("LoginActivity", "Correo guardado (leer): $savedEmail")
        Log.d("LoginActivity", "Contraseña guardada (leer): $savedPassword")
        Log.d("LoginActivity", "Correo ingresado: $emailInput")
        Log.d("LoginActivity", "Contraseña ingresada: $passwordInput")

        if (emailInput == savedEmail && passwordInput == savedPassword) {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
            finish()
        } else {
            Log.d("LoginActivity", "Las credenciales no coinciden")
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }


}
