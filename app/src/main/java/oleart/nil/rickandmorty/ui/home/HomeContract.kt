package oleart.nil.rickandmorty.ui.home

import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.CharactersContract
import oleart.nil.rickandmorty.ui.DataBaseContract

interface HomeContract : CharactersContract {

    interface Presenter : CharactersContract.Presenter, DataBaseContract.Presenter {

        fun getMoreCharacters(actualPage: Int)
        fun setInitCharacters(characters: MutableList<Character?>)
        fun getLastLoadedPage(): Int
        fun updateDB(characters: MutableList<Character?>)
        fun onCharacterClick(character: Character)
        fun getAllCharacters(characters: MutableList<Character?>)
    }

    interface View {

        fun addMoreCharacters(characters: MutableList<Character?>)
        fun setCharacterFromDB(character: Character)
        fun setCharacter(character: Character)
        fun hideLoading()
        fun showError()
        fun updateRV(characters: MutableList<Character?>, indexs: List<Int>)
    }
}