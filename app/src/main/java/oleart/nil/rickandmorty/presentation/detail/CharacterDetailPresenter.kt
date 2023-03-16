package oleart.nil.rickandmorty.presentation.detail

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
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
) : CharacterDetailContract.Presenter, CoroutineScope {

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
            getCharacterDescriptionSuccess(this.character.description!!)
        }
    }

    override fun addToFavorite(character: Character) {
        databaseInteractor.update(character.toCharacterEntity())
    }

    private fun getCharacterDescriptionError(dataSourceError: DataSourceError) {
        dataSourceError.error.message?.let { view.showError(it) }
        view.disableDescription()
        view.showBasicData()
    }

    private fun getCharacterDescriptionSuccess(message: String) {
        character.description = message
        view.setDescription(message)
    }
}