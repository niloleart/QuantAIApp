package oleart.nil.rickandmorty.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.domain.model.Info

@Entity(tableName = CharactersEntity.TABLE_NAME)
data class CharactersEntity(
    @PrimaryKey(autoGenerate = false) val uid: Int = 0,
    @ColumnInfo(name = "info") val info: Info,
    @ColumnInfo(name = "characters") val characters: MutableList<Character?>
) {

    companion object {

        const val TABLE_NAME = "Characters"
    }
}

//data class Info(
//    val count: Int,
//    val pages: Int,
//    val nextPage: String,
//    val prevPage: String?
//)

class CharactersTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun infoFromJson(info: String): Info {
        return gson.fromJson(info, Info::class.java)
    }

    @TypeConverter
    fun infoToJson(info: Info): String {
        return gson.toJson(info)
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
