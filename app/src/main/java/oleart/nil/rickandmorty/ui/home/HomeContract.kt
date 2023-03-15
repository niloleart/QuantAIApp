package oleart.nil.rickandmorty.ui.home

import oleart.nil.rickandmorty.domain.model.Characters

interface HomeContract {

    interface Presenter {

        fun getMoreCharacters(actualPage: Int)
        fun updateDB(characters: Characters)
        fun getLastLoadedPage(page: String): Int
    }

    interface View {

        fun addMoreCharacters(characters: Characters)
    }
}