package edu.uade.primerparcial.data.remote

import com.google.gson.annotations.SerializedName
import edu.uade.primerparcial.domain.model.Pokemon

data class PokemonListResponse(
    @SerializedName("results") val results: List<Pokemon>
)
