package oleart.nil.rickandmorty.presentation.splash

import oleart.nil.rickandmorty.domain.model.Character

interface SplashContract {

    interface Presenter {

        fun initialize()
    }

    interface View {

        fun showLoading()
        fun showError()
        fun hideLoading()
        fun goToHome(characters: MutableList<Character>)
    }
}