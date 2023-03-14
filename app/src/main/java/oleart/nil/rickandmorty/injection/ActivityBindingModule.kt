package oleart.nil.rickandmorty.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import oleart.nil.rickandmorty.injection.scopes.PerActivity
import oleart.nil.rickandmorty.presentation.injection.SplashActivityModule
import oleart.nil.rickandmorty.presentation.splash.SplashActivity

@Module
abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun bindFirebaseTrackingsActivity(): SplashActivity
}