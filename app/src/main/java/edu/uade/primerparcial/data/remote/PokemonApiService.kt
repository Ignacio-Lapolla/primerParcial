package edu.uade.primerparcial.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemons(@Query("limit") limit: Int = 251): PokemonListResponse
}
