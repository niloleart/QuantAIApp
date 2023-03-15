package oleart.nil.rickandmorty.presentation.detail

import oleart.nil.rickandmorty.domain.model.Character

interface CharacterDetailContract {

    interface Presenter {

        fun getDescription(character: Character)
    }

    interface View {

        fun setDescription(description: String)
        fun disableDescription()
    }
}