package edu.uade.primerparcial.ui.state

import edu.uade.primerparcial.domain.model.Pokemon

data class PokemonUiState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
