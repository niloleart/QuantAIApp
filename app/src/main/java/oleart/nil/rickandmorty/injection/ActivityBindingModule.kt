package oleart.nil.rickandmorty.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import oleart.nil.rickandmorty.injection.scopes.PerActivity
import oleart.nil.rickandmorty.presentation.detail.CharacterDetailActivity
import oleart.nil.rickandmorty.presentation.injection.CharacterDetailModule
import oleart.nil.rickandmorty.presentation.injection.SplashActivityModule
import oleart.nil.rickandmorty.presentation.splash.SplashActivity

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun bindSplashActivity(): SplashActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [CharacterDetailModule::class])
    abstract fun bindCharacterDetailActivity(): CharacterDetailActivity
}