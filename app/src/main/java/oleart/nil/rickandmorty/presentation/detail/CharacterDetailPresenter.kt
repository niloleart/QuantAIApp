package oleart.nil.rickandmorty.presentation.detail

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.BaseCoroutine
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.domain.model.Character
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CharacterDetailPresenter @Inject constructor(
    private val interactor: RickAndMortyInteractor,
    private val view: CharacterDetailContract.View,
    private val databaseInteractor: DatabaseInteractor
) : CharacterDetailContract.Presenter, BaseCoroutine() {

    private lateinit var character: Character

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun getDescription(character: Character) {
        this.character = character
        if (this.character.description.isNullOrEmpty()) {
            launch {
                interactor.getCharacterDescription(character).either(
                    ::getCharacterDescriptionError, ::getCharacterDescriptionSuccess
                )
            }
        } else {
            setDescription()
        }
    }

    override fun addToFavorite(character: Character) {
        databaseInteractor.update(character.toCharacterEntity())
    }

    override fun updateCharacter(character: Character) {
        databaseInteractor.update(character.toCharacterEntity())
    }

    override fun onBackPressed() {
        super.stopCoroutine()
    }

    private fun getCharacterDescriptionError(dataSourceError: DataSourceError) {
        dataSourceError.error.message?.let { view.showError(it) }
        view.disableDescription()
        view.showBasicData()
    }

    private fun getCharacterDescriptionSuccess(message: String) {
        character.description = message
        databaseInteractor.update(character.toCharacterEntity())
        setDescription()
    }

    private fun setDescription() {
        view.setDescription(character.description!!)
    }
}