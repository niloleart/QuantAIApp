package oleart.nil.rickandmorty.presentation.splash

import oleart.nil.rickandmorty.domain.model.Characters

interface SplashContract {

    interface Presenter {

        fun initialize()
    }

    interface View {

        fun showLoading()
        fun showError()
        fun hideLoading()
        fun goToHome(characters: Characters)
    }
}