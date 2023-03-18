package oleart.nil.rickandmorty.remoteconfiguration

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder
import javax.inject.Inject

class RemoteConfiguration @Inject constructor(
    private var firebaseRemoteConfig: FirebaseRemoteConfig
) {

    companion object {

        const val OPEN_AI_KEY = "open_ai_key"
    }

    fun getOpenAIKey(): String {
//        forceRetrieveImmediateChanges()
        return firebaseRemoteConfig.getString(OPEN_AI_KEY)
    }

    private fun forceRetrieveImmediateChanges() {
        val configSettings = Builder()
            .setMinimumFetchIntervalInSeconds(0)
            .build()
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)

        firebaseRemoteConfig.fetch().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseRemoteConfig.activate()
                // The updated values are now available
            } else {
                // Failed to fetch values
            }
        }
    }
}