package oleart.nil.rickandmorty.db

import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.domain.model.Location

@Entity(tableName = CharacterEntity.TABLE_NAME, primaryKeys = ["id"])
data class CharacterEntity(
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
    val created: String,
    var description: String? = null,
    var isFavorite: Boolean = false
) {

    companion object {

        const val TABLE_NAME = "Characters"
    }

    fun toCharacter(): Character {
        return Character(
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

class CharactersTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun infoFromJson(location: String): Location {
        return gson.fromJson(location, Location::class.java)
    }

    @TypeConverter
    fun infoToJson(location: Location): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun characterListFromJson(characterList: String): MutableList<Character?> {
        val type = object : TypeToken<MutableList<Character?>>() {}.type
        return gson.fromJson(characterList, type)
    }

    @TypeConverter
    fun characterListToJson(characters: MutableList<Character?>): String {
        return gson.toJson(characters)
    }
}
