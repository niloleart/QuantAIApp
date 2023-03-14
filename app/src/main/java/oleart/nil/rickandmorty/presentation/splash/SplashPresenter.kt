package oleart.nil.rickandmorty.presentation.splash

import android.content.Context
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.domain.model.Characters
import oleart.nil.rickandmorty.presentation.splash.SplashContract.View
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SplashPresenter @Inject constructor(
    private val context: Context,
    private val interactor: RickAndMortyInteractor,
    private val view: View
) : SplashContract.Presenter, CoroutineScope {

    override fun initialize() {
        launch {
            interactor.getAllCharacters().either(
                ::getAllCharactersError, ::getAllCharactersSuccess
            )
        }
    }

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    private fun getAllCharactersError(dataSourceError: DataSourceError) {
        view.hideLoading()
        view.showError()
    }

    private fun getAllCharactersSuccess(characters: Characters) {
        Handler(Looper.getMainLooper()).postDelayed({
            view.hideLoading()
            view.goToHome(characters)
        }, 2000)
    }
}