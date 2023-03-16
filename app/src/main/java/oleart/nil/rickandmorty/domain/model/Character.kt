package oleart.nil.rickandmorty.domain.model

import oleart.nil.rickandmorty.db.CharacterEntity
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
) : Serializable {

    fun toCharacterEntity(): CharacterEntity {
        return CharacterEntity(
            id = this.id,
            name = this.name,
            status = this.status,
            species = this.species,
            type = this.type,
            gender = this.gender,
            origin = this.origin,
            location = this.location,
            image = this.image,
            episode = this.episode,
            url = this.url,
            created = this.created,
            description = this.description,
            isFavorite = this.isFavorite
        )
    }
}