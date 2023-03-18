package oleart.nil.rickandmorty.ui.main

import kotlinx.coroutines.runBlocking
import oleart.nil.rickandmorty.base.BaseCoroutine
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.model.Character
import javax.inject.Inject

class MainActivityPresenter @Inject constructor(
    private val databaseInteractor: DatabaseInteractor
) : MainActivityContract.Presenter, BaseCoroutine() {

    override fun getFavCharacters(): List<Character?> =
        runBlocking {
            databaseInteractor.getFavCharacters()
        }
}