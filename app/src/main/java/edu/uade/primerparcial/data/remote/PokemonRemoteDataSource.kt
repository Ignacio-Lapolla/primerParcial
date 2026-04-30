package edu.uade.primerparcial.data.remote

import edu.uade.primerparcial.data.RetrofitInstance
import edu.uade.primerparcial.domain.model.Pokemon

class PokemonRemoteDataSource(
    private val api: PokemonApiService = RetrofitInstance.api
) {
    suspend fun getPokemons(): List<Pokemon> = api.getPokemons().results
}
