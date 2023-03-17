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

    suspend fun getCharactersCount(): Int {
        return charactersDao.getCharacterCount()
    }

    fun update(charactersEntity: CharacterEntity) =
        launch {
            charactersDao.insertCharacter(charactersEntity)
        }

    fun addToFavorite(character: Character) {
        launch {
//            charactersDao.updateCharacter(character)
        }
    }

    suspend fun deleteAll() = charactersDao.deleteAllCharacters()

//    suspend fun getCharactersCount() = charactersDao.getCharactersCount()
}