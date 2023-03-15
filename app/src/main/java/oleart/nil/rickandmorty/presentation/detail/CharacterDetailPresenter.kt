package oleart.nil.rickandmorty.presentation.detail

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.base.errors.RickAndMortyError
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.domain.model.Character
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CharacterDetailPresenter @Inject constructor(
    private val interactor: RickAndMortyInteractor,
    private val view: CharacterDetailContract.View
) : CharacterDetailContract.Presenter, CoroutineScope {

    private lateinit var character: Character

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun getDescription(character: Character) {
        this.character = character
        if (this.character.description.isNullOrEmpty()) {
            launch {
//                interactor.getCharacterDescription(character).either(
//                    ::getCharacterDescriptionError, ::getCharacterDescriptionSuccess)
                getCharacterDescriptionError(DataSourceError((RickAndMortyError())))
            }
        } else {
            getCharacterDescriptionSuccess(this.character.description!!)
        }
    }

    private fun getCharacterDescriptionError(dataSourceError: DataSourceError) {
        view.disableDescription()
    }

    private fun getCharacterDescriptionSuccess(message: String) {
        character.description = message
        view.setDescription(message)
    }
}