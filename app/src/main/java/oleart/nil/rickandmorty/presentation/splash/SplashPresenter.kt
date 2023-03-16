package oleart.nil.rickandmorty.presentation.splash

import android.content.Context
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.BaseCoroutine
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.db.CharacterEntity
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.domain.model.Characters
import oleart.nil.rickandmorty.presentation.splash.SplashContract.View
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SplashPresenter @Inject constructor(
    private val context: Context,
    private val interactor: RickAndMortyInteractor,
    private val view: View,
    private val databaseInteractor: DatabaseInteractor
) : SplashContract.Presenter, BaseCoroutine() {

    override fun initialize() {
        launch {
            if (databaseInteractor.getCharactersCount() > 0) {
                getFromDB()
            } else {
                getFromInternet()
            }
        }
    }

    private fun getFromInternet() {
        launch {
            interactor.getAllCharacters().either(
                ::getAllCharactersError, ::getAllCharactersSuccess
            )
        }
    }

    private suspend fun getFromDB() {
        launchAsync {
            val storedCharacters = databaseInteractor.getStoredCharacters()
            goToHome(storedCharacters)
        }
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    private fun getAllCharactersError(dataSourceError: DataSourceError) {
        view.hideLoading()
        view.showError()
    }

    private fun getAllCharactersSuccess(characters: Characters) {
        saveCharactersInDB(characters.characters)
        goToHome(characters.characters)
    }

    private fun goToHome(characters: MutableList<Character>) {
        Handler(Looper.getMainLooper()).postDelayed({
            view.hideLoading()
            view.goToHome(characters)
        }, 2000)
    }

    private fun saveCharactersInDB(characters: MutableList<Character>) {
        val characterEntities = characters.map { character ->
            CharacterEntity(
                id = character.id,
                name = character.name,
                status = character.status,
                species = character.species,
                type = character.type,
                gender = character.gender,
                origin = character.origin,
                location = character.location,
                image = character.image,
                episode = character.episode,
                url = character.url,
                created = character.created,
                description = character.description,
                isFavorite = character.isFavorite
            )
        }

        databaseInteractor.saveCharacters(characterEntities)
    }
}