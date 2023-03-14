package oleart.nil.rickandmorty.ui.home

import oleart.nil.rickandmorty.domain.model.Characters

interface HomeContract {

    interface Presenter {

        fun getMoreCharacters(actualPage: Int)
    }

    interface View {

        fun addMoreCharacters(characters: Characters)
    }
}