package edu.uade.primerparcial.data.repository

import edu.uade.primerparcial.data.remote.PokemonRemoteDataSource
import edu.uade.primerparcial.domain.model.Pokemon

class PokemonRepository(
    private val remoteDataSource: PokemonRemoteDataSource = PokemonRemoteDataSource()
) {
    suspend fun getPokemons(): List<Pokemon> = remoteDataSource.getPokemons()
}
