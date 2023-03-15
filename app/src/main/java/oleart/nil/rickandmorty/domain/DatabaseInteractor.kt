package oleart.nil.rickandmorty.domain

import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.BaseCoroutine
import oleart.nil.rickandmorty.db.CharactersDao
import oleart.nil.rickandmorty.db.CharactersEntity

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

    suspend fun deleteAll() = charactersDao.deleteAllCharacters()

    suspend fun getCharactersCount() = charactersDao.getCharactersCount()
}