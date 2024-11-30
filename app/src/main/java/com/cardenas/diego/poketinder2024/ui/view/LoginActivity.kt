package com.cardenas.diego.poketinder2024.ui.view

import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.cardenas.diego.poketinder2024.databinding.ActivityLoginBinding
import com.cardenas.diego.poketinder2024.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        observeValues()
    }

    private fun observeValues() {
        loginViewModel.inputsError.observe(this) {
            Toast.makeText(this, "Ingrese los datos completos", Toast.LENGTH_SHORT).show()
        }

        loginViewModel.authError.observe(this) {
            Toast.makeText(this, "Error usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }

        loginViewModel.loginSuccess.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            loginViewModel.validateInputs(email, password)
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
