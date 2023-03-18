package oleart.nil.rickandmorty.ui.main

import oleart.nil.rickandmorty.domain.model.Character

interface MainActivityContract {

    interface Presenter {

        fun getFavCharacters(): List<Character?>
    }
}