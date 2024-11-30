package com.cardenas.diego.poketinder2024.data.model

data class PokemonListResponse (
    val count: Int,
    val next: String,
    val results: List<PokemonResponse>
)