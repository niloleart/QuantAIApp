package oleart.nil.rickandmorty.presentation.injection

import dagger.Module
import dagger.Provides
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.injection.scopes.PerFragment
import oleart.nil.rickandmorty.ui.home.HomeContract
import oleart.nil.rickandmorty.ui.home.HomeFragment
import oleart.nil.rickandmorty.ui.home.HomePresenter

@Module
class HomeFragmentModule {

    @Provides
    @PerFragment
    internal fun providesView(view: HomeFragment): HomeContract.View {
        return view
    }

    @Provides
    @PerFragment
    internal fun providesPresenter(
        view: HomeContract.View,
        interactor: RickAndMortyInteractor
    ): HomeContract.Presenter {
        return HomePresenter(view, interactor)
    }
}