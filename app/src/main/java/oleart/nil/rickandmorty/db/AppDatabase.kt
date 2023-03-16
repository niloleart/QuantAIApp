package oleart.nil.rickandmorty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CharacterEntity::class],
    version = 5
)

@TypeConverters(CharactersTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
}