package oleart.nil.rickandmorty.presentation.injection

import dagger.Module
import dagger.Provides
import oleart.nil.rickandmorty.db.CharactersDao
import oleart.nil.rickandmorty.domain.DatabaseInteractor
import oleart.nil.rickandmorty.domain.RickAndMortyInteractor
import oleart.nil.rickandmorty.injection.scopes.PerActivity
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailActivity
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailContract
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailPresenter

@Module
class CharacterDetailModule {

    @Provides
    @PerActivity
    internal fun providesView(view: CharacterDetailActivity): CharacterDetailContract.View {
        return view
    }

    @Provides
    @PerActivity
    internal fun providesCharacterDetailPresenter(
        interactor: RickAndMortyInteractor,
        view: CharacterDetailContract.View,
        databaseInteractor: DatabaseInteractor
    ): CharacterDetailContract.Presenter = CharacterDetailPresenter(interactor, view, databaseInteractor)

    @Provides
    @PerActivity
    internal fun providesDatabaseInteractor(
        charactersDao: CharactersDao
    ): DatabaseInteractor {
        return DatabaseInteractor(charactersDao)
    }
}