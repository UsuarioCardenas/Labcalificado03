package com.cardenas.diego.laboratoriocalificado03

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://private-effe28-tecsup1.apiary-mock.com/"

    val teacherService: TeacherService by lazy {
        createService(TeacherService::class.java)
    }

    private fun <T> createService(service: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
    }
}