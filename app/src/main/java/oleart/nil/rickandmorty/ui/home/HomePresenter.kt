package oleart.nil.rickandmorty.ui.home

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.db.CharactersEntity
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.domain.model.Characters
import kotlin.coroutines.CoroutineContext

class HomePresenter(
    private val view: HomeContract.View,
    private val context: Context,
    private val interactor: RickAndMortyInteractor,
    private val databaseInteractor: DatabaseInteractor
) : HomeContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    override fun getMoreCharacters(actualPage: Int) {
        launch {
            interactor.getMoreCharacters(actualPage + 1).either(
                ::getMoreCharactersError, ::getMoreCharactersSuccess
            )
        }
    }

    override fun getLastLoadedPage(page: String): Int {
        val split = page.split("=")
        return split[split.lastIndex].toInt()
    }

    override fun updateDB(characters: Characters) {
        databaseInteractor.update(CharactersEntity(info = characters.info, characters = characters.characters))
    }

    private fun getMoreCharactersError(dataSourceError: DataSourceError) {
    }

    private fun getMoreCharactersSuccess(characters: Characters) {
        view.addMoreCharacters(characters)
    }
}