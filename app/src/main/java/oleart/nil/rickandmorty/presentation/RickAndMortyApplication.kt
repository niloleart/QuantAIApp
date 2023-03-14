package oleart.nil.rickandmorty.presentation

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import oleart.nil.rickandmorty.injection.ApplicationComponent
import oleart.nil.rickandmorty.injection.DaggerApplicationComponent

class RickAndMortyApplication : DaggerApplication() {

    protected lateinit var context: Context

    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        context = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
        return component
    }
}