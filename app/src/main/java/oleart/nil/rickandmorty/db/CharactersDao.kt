package oleart.nil.rickandmorty.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CharactersDao {

    @Query("SELECT * FROM Characters")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM Characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterEntity?>)

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Delete
    suspend fun deleteCharacter(character: CharacterEntity)

    @Query("SELECT COUNT(*) FROM Characters")
    suspend fun getCharacterCount(): Int

    @Query("DELETE FROM Characters")
    suspend fun deleteAllCharacters()
}