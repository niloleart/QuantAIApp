package oleart.nil.rickandmorty.domain

import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.BaseCoroutine
import oleart.nil.rickandmorty.db.CharactersDao
import oleart.nil.rickandmorty.db.CharactersEntity
import oleart.nil.rickandmorty.domain.model.Character

class DatabaseInteractor(
    private val charactersDao: CharactersDao
) : BaseCoroutine() {

    fun getStoredCharacters() = charactersDao.readCharacters()

    fun saveCharacters(charactersEntity: CharactersEntity) {
        launch {
            charactersDao.insertCharacter(charactersEntity)
        }
    }

    fun update(charactersEntity: CharactersEntity) =
        launch {
            charactersDao.insertCharacter(charactersEntity)
        }

    fun addToFavorite(character: Character) {
        launch {
            charactersDao.updateCharacter(character)
        }
    }

    suspend fun deleteAll() = charactersDao.deleteAllCharacters()

    suspend fun getCharactersCount() = charactersDao.getCharactersCount()
}