package oleart.nil.rickandmorty.presentation.injection

import dagger.Module
import dagger.Provides
import oleart.nil.rickandmorty.db.CharactersDao
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.injection.scopes.PerFragment
import oleart.nil.rickandmorty.ui.favs.FavsContract
import oleart.nil.rickandmorty.ui.favs.FavsFragment
import oleart.nil.rickandmorty.ui.favs.FavsPresenter

@Module
class FavsFragmentModule {

    @Provides
    @PerFragment
    internal fun providesView(view: FavsFragment): FavsContract.View {
        return view
    }

    @Provides
    @PerFragment
    internal fun providesPresenter(
        view: FavsContract.View,
        databaseInteractor: DatabaseInteractor
    ): FavsContract.Presenter {
        return FavsPresenter(view, databaseInteractor)
    }

    @Provides
    @PerFragment
    internal fun providesDatabaseInteractor(
        charactersDao: CharactersDao
    ): DatabaseInteractor {
        return DatabaseInteractor(charactersDao)
    }
}