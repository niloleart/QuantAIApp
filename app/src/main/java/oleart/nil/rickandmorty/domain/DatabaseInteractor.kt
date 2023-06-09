package oleart.nil.rickandmorty.domain

import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.BaseCoroutine
import oleart.nil.rickandmorty.db.CharacterEntity
import oleart.nil.rickandmorty.db.CharactersDao
import oleart.nil.rickandmorty.domain.model.Character

class DatabaseInteractor(
    private val charactersDao: CharactersDao
) : BaseCoroutine() {

    suspend fun getStoredCharacters(): MutableList<Character> {
        val characterEntities = charactersDao.getAllCharacters()
        return characterEntities.map { it.toCharacter() }.toMutableList()
    }

    suspend fun getCharacter(id: Int): Character {
        val characterEntity = charactersDao.getCharacterById(id)
        return characterEntity.toCharacter()
    }

    fun saveCharacters(charactersEntity: List<CharacterEntity?>) {
        launch {
            charactersDao.insertAll(charactersEntity)
        }
    }

    suspend fun getFavCharacters(): List<Character?> {
        val charactersEntities = charactersDao.getFavCharacters()
        return charactersEntities.map { it.toCharacter() }
    }

    suspend fun deleteAllFavorites() {
        charactersDao.deleteAllFavorites()
    }

    suspend fun getCharactersCount(): Int {
        return charactersDao.getCharacterCount()
    }

    fun update(charactersEntity: CharacterEntity) =
        launch {
            charactersDao.insertCharacter(charactersEntity)
        }
}