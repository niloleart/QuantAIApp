package oleart.nil.rickandmorty.injection.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import oleart.nil.rickandmorty.injection.scopes.PerApplication

@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }
}