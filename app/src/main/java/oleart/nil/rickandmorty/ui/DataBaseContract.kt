package oleart.nil.rickandmorty.ui

import oleart.nil.rickandmorty.domain.model.Characters

interface DataBaseContract {

    interface Presenter {

        fun updateDB(characters: Characters)
    }
}