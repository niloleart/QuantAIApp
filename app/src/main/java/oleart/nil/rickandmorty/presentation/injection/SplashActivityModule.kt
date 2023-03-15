package oleart.nil.rickandmorty.presentation.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import oleart.nil.rickandmorty.db.CharactersDao
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.injection.scopes.PerActivity
import oleart.nil.rickandmorty.presentation.splash.SplashActivity
import oleart.nil.rickandmorty.presentation.splash.SplashContract
import oleart.nil.rickandmorty.presentation.splash.SplashPresenter

@Module
open class SplashActivityModule {

    @Provides
    @PerActivity
    internal fun providesView(view: SplashActivity): SplashContract.View {
        return view
    }

    @Provides
    @PerActivity
    internal fun providesSplashPresenter(
        context: Context,
        interactor: RickAndMortyInteractor,
        view: SplashContract.View,
        databaseInteractor: DatabaseInteractor
    ): SplashContract.Presenter = SplashPresenter(context, interactor, view, databaseInteractor)

    @Provides
    @PerActivity
    internal fun providesDatabaseInteractor(
        charactersDao: CharactersDao
    ): DatabaseInteractor {
        return DatabaseInteractor(charactersDao)
    }
}