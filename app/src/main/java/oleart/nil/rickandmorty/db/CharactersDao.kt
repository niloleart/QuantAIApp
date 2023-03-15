package oleart.nil.rickandmorty.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(charactersEntity: CharactersEntity)

    @Query("SELECT * FROM Characters ORDER BY uid ASC")
    fun readCharacters(): CharactersEntity

    @Query("SELECT COUNT(*) FROM Characters")
    suspend fun getCharactersCount(): Int

    @Query("DELETE FROM Characters")
    suspend fun deleteAllCharacters()
}