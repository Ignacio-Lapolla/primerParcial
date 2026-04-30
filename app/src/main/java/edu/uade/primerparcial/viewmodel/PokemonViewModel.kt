package edu.uade.primerparcial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.uade.primerparcial.data.repository.PokemonRepository
import edu.uade.primerparcial.ui.state.PokemonUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
            _uiState.update { PokemonUiState(isLoading = true) }
            try {
                val pokemons = repository.getPokemons()
                _uiState.update { PokemonUiState(pokemons = pokemons) }
            } catch (e: Exception) {
                _uiState.update { PokemonUiState(error = "Error al cargar los pokémons") }
            }
        }
    }
}
