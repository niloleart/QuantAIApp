package oleart.nil.rickandmorty.presentation.injection

import dagger.Module
import dagger.Provides
import oleart.nil.rickandmorty.db.CharactersDao
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.injection.scopes.PerActivity
import oleart.nil.rickandmorty.ui.main.MainActivityContract
import oleart.nil.rickandmorty.ui.main.MainActivityPresenter

@Module
open class MainActivityModule {

    @Provides
    @PerActivity
    internal fun providesMainActivityPresenter(
        databaseInteractor: DatabaseInteractor
    ): MainActivityContract.Presenter = MainActivityPresenter(databaseInteractor)

    @Provides
    @PerActivity
    internal fun providesDatabaseInteractor(
        charactersDao: CharactersDao
    ): DatabaseInteractor {
        return DatabaseInteractor(charactersDao)
    }
}