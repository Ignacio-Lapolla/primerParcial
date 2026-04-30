package edu.uade.primerparcial.data.repository

import edu.uade.primerparcial.data.RetrofitInstance
import edu.uade.primerparcial.data.PokemonApiService
import edu.uade.primerparcial.model.Pokemon

class PokemonRepository(
    private val api: PokemonApiService = RetrofitInstance.api
) {
    suspend fun getPokemons(): List<Pokemon> = api.getPokemons().results
}
