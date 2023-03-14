package oleart.nil.rickandmorty.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import oleart.nil.rickandmorty.injection.modules.ApplicationModule
import oleart.nil.rickandmorty.injection.scopes.PerApplication
import oleart.nil.rickandmorty.presentation.RickAndMortyApplication

@PerApplication
@Component(
    modules = [
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        ApplicationModule::class,
        AndroidSupportInjectionModule::class
    ]
)

interface ApplicationComponent : AndroidInjector<RickAndMortyApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }
}