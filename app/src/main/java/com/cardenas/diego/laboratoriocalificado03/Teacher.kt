package com.cardenas.diego.laboratoriocalificado03

data class Teacher(
    val name: String,
    val lastName: String, // Cambié de "surname" a "lastName" para ser más claro
    val phone: String,
    val email: String,
    val imageUrl: String
)