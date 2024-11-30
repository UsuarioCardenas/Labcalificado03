package com.cardenas.diego.poketinder2024.data.network

import com.cardenas.diego.poketinder2024.data.model.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET


interface PokemonApi {
    @GET("api/v2/pokemon")
    suspend fun getPokemons(): Response<PokemonListResponse>
}