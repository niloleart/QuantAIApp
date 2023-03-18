package oleart.nil.rickandmorty.ui.favs

import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.CharactersContract

interface FavsContract : CharactersContract {

    interface Presenter : CharactersContract.Presenter {

        fun getFavCharacters()
    }

    interface View {

        fun setRV(favs: List<Character?>)
        fun showPlaceholder()
    }
}