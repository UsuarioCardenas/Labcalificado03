package com.cardenas.diego.laboratoriocalificado03

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TeacherService {
    @GET("list/teacher")
    suspend fun getTeachers(): TeacherResponse
}