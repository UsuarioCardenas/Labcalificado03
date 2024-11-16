package com.cardenas.diego.laboratoriocalificado03

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cardenas.diego.laboratoriocalificado03.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val teacherAdapter by lazy {
        TeacherAdapter(emptyList(), this::callTeacher, this::emailTeacher)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración de RecyclerView
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
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${teacher.phone}"))
        startActivity(intent)
    }

    private fun emailTeacher(teacher: Teacher) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${teacher.email}")
        }
        startActivity(intent)
    }
}