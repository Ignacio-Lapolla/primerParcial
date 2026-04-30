package edu.uade.primerparcial.data.remote

import edu.uade.primerparcial.domain.model.Pokemon

data class PokemonListResponse(
    val results: List<Pokemon>
)
