package oleart.nil.rickandmorty.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import oleart.nil.rickandmorty.injection.scopes.PerFragment
import oleart.nil.rickandmorty.presentation.injection.FavsFragmentModule
import oleart.nil.rickandmorty.presentation.injection.HomeFragmentModule
import oleart.nil.rickandmorty.ui.favs.FavsFragment
import oleart.nil.rickandmorty.ui.home.HomeFragment

@Module
abstract class FragmentBindingModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun bindHomeFragment(): HomeFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [FavsFragmentModule::class])
    abstract fun bindFavsFragment(): FavsFragment
}