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
    private lateinit var characters: MutableList<Character?>
    private var lastLoadedPage = 0

    override fun setInitCharacters(characters: MutableList<Character?>) {
        this.characters = characters
    }

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
        this.characters = characters
        val entities = this.characters.map { it!!.toCharacterEntity() }.toMutableList()
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

    override fun getAllCharacters(characters: MutableList<Character?>) {
        launch {
            val databaseCharacters = databaseInteractor.getStoredCharacters()
            needsUpdate(databaseCharacters)
        }
    }

    private fun needsUpdate(databaseCharacters: MutableList<Character>) {
        val diffCharacters = databaseCharacters.minus(characters)
        if (diffCharacters.isNotEmpty()) {
            val indexs = diffCharacters.map { it!!.id - 1 }
            for (index in indexs) {
                characters[index] = diffCharacters.find {
                    it!!.id == characters[index]!!.id
                }
            }
            updateRV(characters, indexs)
        }
    }

    private fun updateRV(characters: MutableList<Character?>, indexs: List<Int>) {
        view.updateRV(characters, indexs)
    }

    private fun getMoreCharactersError(dataSourceError: DataSourceError) {
        view.hideLoading()
        view.showError()
    }

    private fun getMoreCharactersSuccess(characters: Characters) {
        this.characters = characters.characters.toMutableList()
        setLastLoadedPage()
        view.addMoreCharacters(this.characters)
    }
}