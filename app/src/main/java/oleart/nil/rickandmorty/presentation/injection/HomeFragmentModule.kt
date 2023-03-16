package oleart.nil.rickandmorty.presentation.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import oleart.nil.rickandmorty.data.preferences.AppSharedPreferences
import oleart.nil.rickandmorty.db.CharactersDao
import oleart.nil.rickandmorty.domain.DatabaseInteractor
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
        context: Context,
        interactor: RickAndMortyInteractor,
        databaseInteractor: DatabaseInteractor,
        sharedPreferences: AppSharedPreferences
    ): HomeContract.Presenter {
        return HomePresenter(view, context, interactor, databaseInteractor, sharedPreferences)
    }

    @Provides
    @PerFragment
    internal fun providesDatabaseInteractor(
        charactersDao: CharactersDao
    ): DatabaseInteractor {
        return DatabaseInteractor(charactersDao)
    }
}