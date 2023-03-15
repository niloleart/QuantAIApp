package oleart.nil.rickandmorty.domain.model

import java.io.Serializable

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String, //TODO parse date
    var description: String? = null,
    var isFavorite: Boolean = false

) : Serializable