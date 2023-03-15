package oleart.nil.rickandmorty.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import oleart.nil.rickandmorty.domain.model.Character

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

    @Update
    fun updateCharacter(character: Character)
}