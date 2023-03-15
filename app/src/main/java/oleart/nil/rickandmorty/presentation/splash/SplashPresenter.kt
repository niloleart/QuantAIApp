package oleart.nil.rickandmorty.presentation.splash

import android.content.Context
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.BaseCoroutine
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.db.CharactersEntity
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
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
            goToHome(Characters(storedCharacters.info, storedCharacters.characters))
        }
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    private fun getAllCharactersError(dataSourceError: DataSourceError) {
        view.hideLoading()
        view.showError()
    }

    private fun getAllCharactersSuccess(characters: Characters) {
        saveCharactersInDB(characters)
        goToHome(characters)
    }

    private fun goToHome(characters: Characters) {
        Handler(Looper.getMainLooper()).postDelayed({
            view.hideLoading()
            view.goToHome(characters)
        }, 2000)
    }

    private fun saveCharactersInDB(characters: Characters) {
        databaseInteractor.saveCharacters(
            CharactersEntity(
                info = characters.info,
                characters = characters.characters
            )
        )
    }
}