package edu.uade.primerparcial.domain.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) {
    val id: Int
        get() = url.trimEnd('/').substringAfterLast('/').toIntOrNull() ?: 0

    val spriteUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"

    val nameFormatted: String
        get() = name.replaceFirstChar { it.uppercase() }
}
