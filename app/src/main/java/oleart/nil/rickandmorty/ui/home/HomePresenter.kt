package oleart.nil.rickandmorty.ui.home

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.data.preferences.AppSharedPreferences
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.domain.model.Characters
import kotlin.coroutines.CoroutineContext

class HomePresenter(
    private val view: HomeContract.View,
    private val context: Context,
    private val interactor: RickAndMortyInteractor,
    private val databaseInteractor: DatabaseInteractor,
    private val appSharedPreferences: AppSharedPreferences
) : HomeContract.Presenter, CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    private var lastLoadedPage = 0

    override fun getMoreCharacters(actualPage: Int) {
        launch {
            interactor.getMoreCharacters(getLastLoadedPage() + 1).either(
                ::getMoreCharactersError, ::getMoreCharactersSuccess
            )
        }
    }

    override fun getLastLoadedPage(): Int {
        lastLoadedPage = appSharedPreferences.getLastLoadedPage()
        return lastLoadedPage
    }

    private fun setLastLoadedPage() {
        appSharedPreferences.setLastLoadedPage(lastLoadedPage + 1)
    }

    override fun updateDB(characters: MutableList<Character?>) {
        val entities = characters.map { it!!.toCharacterEntity() }.toMutableList()
        databaseInteractor.saveCharacters(entities)
    }

    override fun onCharacterClick(character: Character) {
        var dbCharacter: Character? = null
        launch {
            dbCharacter = databaseInteractor.getCharacter(character.id)
            if (dbCharacter != null) {
                view.setCharacterFromDB(dbCharacter!!)
            } else {
                view.setCharacter(character)
            }
        }
    }

    private fun getMoreCharactersError(dataSourceError: DataSourceError) {
        view.hideLoading()
        view.showError()
    }

    private fun getMoreCharactersSuccess(characters: Characters) {
        setLastLoadedPage()
        view.addMoreCharacters(characters.characters)
    }
}