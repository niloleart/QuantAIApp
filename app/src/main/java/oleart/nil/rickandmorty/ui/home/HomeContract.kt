package oleart.nil.rickandmorty.ui.home

import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.DataBaseContract

interface HomeContract {

    interface Presenter : DataBaseContract.Presenter {

        fun getMoreCharacters(actualPage: Int)
        fun getLastLoadedPage(): Int
        fun updateDB(characters: MutableList<Character?>)
        fun onCharacterClick(character: Character)
    }

    interface View {

        fun addMoreCharacters(characters: MutableList<Character>)
        fun setCharacterFromDB(character: Character)
        fun setCharacter(character: Character)
        fun hideLoading()
        fun showError()
    }
}