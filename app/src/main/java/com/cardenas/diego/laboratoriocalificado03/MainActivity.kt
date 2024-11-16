package com.cardenas.diego.laboratoriocalificado03

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cardenas.diego.laboratoriocalificado03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val CALL_PHONE_PERMISSION_CODE = 1001
    }
    private val teacherAdapter by lazy {
        TeacherAdapter(emptyList(), this::callTeacher, this::emailTeacher)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_PHONE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido, intenta realizar la llamada nuevamente.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permiso denegado. No se puede realizar la llamada.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this) // Añadir un LayoutManager
        binding.recyclerView.adapter = teacherAdapter
        fetchTeachers()
    }

    private fun fetchTeachers() {
        lifecycleScope.launch {
            try {
                val response = ApiClient.teacherService.getTeachers()
                teacherAdapter.updateData(response.teachers)
                Log.d("API_RESPONSE", "Teachers: ${response.teachers}")
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error al obtener datos", e)
            }
        }
    }

    private fun callTeacher(teacher: Teacher) {
        val phoneNumber = teacher.phone.replace(" ", "").replace("+", "")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PHONE_PERMISSION_CODE)
        }
    }



    private fun emailTeacher(teacher: Teacher) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${teacher.email}")
        }
        startActivity(intent)
    }
}