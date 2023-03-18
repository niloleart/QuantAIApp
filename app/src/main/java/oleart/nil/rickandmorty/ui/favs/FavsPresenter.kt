package oleart.nil.rickandmorty.ui.favs

import kotlinx.coroutines.launch
import oleart.nil.rickandmorty.base.BaseCoroutine
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.model.Character

class FavsPresenter(
    private val view: FavsContract.View,
    private val databaseInteractor: DatabaseInteractor
) : FavsContract.Presenter, BaseCoroutine() {

    override fun getFavCharacters() {
        launch {
            val favs = databaseInteractor.getFavCharacters()
            if (favs.isNotEmpty()) {
                getFavsSuccess(favs)
            } else {
                getFavsError()
            }
        }
    }

    private fun getFavsSuccess(favs: List<Character?>) {
        view.setRV(favs)
    }

    private fun getFavsError() {
        view.showPlaceholder()
    }
}