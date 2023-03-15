package oleart.nil.rickandmorty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CharactersEntity::class],
    version = 3
)

@TypeConverters(CharactersTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao
}