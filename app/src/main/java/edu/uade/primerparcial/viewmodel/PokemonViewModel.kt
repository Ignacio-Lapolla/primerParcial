package edu.uade.primerparcial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uade.primerparcial.data.repository.PokemonRepository
import edu.uade.primerparcial.domain.model.Pokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PokemonUiState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class PokemonViewModel(
    private val repository: PokemonRepository = PokemonRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PokemonUiState())
    val uiState: StateFlow<PokemonUiState> = _uiState.asStateFlow()

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch {
            _uiState.value = PokemonUiState(isLoading = true)
            try {
                val pokemons = repository.getPokemons()
                _uiState.value = PokemonUiState(pokemons = pokemons)
            } catch (e: Exception) {
                _uiState.value = PokemonUiState(error = "Error al cargar los pokémons")
            }
        }
    }
}
