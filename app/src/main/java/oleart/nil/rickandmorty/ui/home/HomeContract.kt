package oleart.nil.rickandmorty.ui.home

import oleart.nil.rickandmorty.domain.model.Characters
import oleart.nil.rickandmorty.ui.DataBaseContract

interface HomeContract {

    interface Presenter : DataBaseContract.Presenter {

        fun getMoreCharacters(actualPage: Int)
        fun getLastLoadedPage(page: String): Int
    }

    interface View {
        fun addMoreCharacters(characters: Characters)
    }
}