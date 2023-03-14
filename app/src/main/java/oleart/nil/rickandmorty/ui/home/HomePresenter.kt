package oleart.nil.rickandmorty.ui.home

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.domain.model.Characters
import kotlin.coroutines.CoroutineContext

class HomePresenter(
    private val view: HomeContract.View,
    private val interactor: RickAndMortyInteractor
) : HomeContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun getMoreCharacters(actualPage: Int) {
        launch {
            interactor.getMoreCharacters(actualPage + 1).either(
                ::getMoreCharactersError, ::getMoreCharactersSuccess
            )
        }
    }

    private fun getMoreCharactersError(dataSourceError: DataSourceError) {
    }

    private fun getMoreCharactersSuccess(characters: Characters) {
        view.addMoreCharacters(characters)
    }
}