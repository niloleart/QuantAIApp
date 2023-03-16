package oleart.nil.rickandmorty.presentation.detail

import oleart.nil.rickandmorty.domain.model.Character
import oleart.nil.rickandmorty.ui.DataBaseContract

interface CharacterDetailContract {

    interface Presenter : DataBaseContract.Presenter {
        fun getDescription(character: Character)
        fun addToFavorite(character: Character)
    }

    interface View {

        fun setDescription(description: String)
        fun disableDescription()
        fun showError(string: String)
        fun showBasicData()
    }
}