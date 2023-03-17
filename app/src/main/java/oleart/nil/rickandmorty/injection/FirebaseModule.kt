package oleart.nil.rickandmorty.injection

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.Module
import dagger.Provides
import oleart.nil.rickandmorty.injection.scopes.PerApplication

@Module
class FirebaseModule {

    @Provides
    @PerApplication
    internal fun providesRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance()
    }
}