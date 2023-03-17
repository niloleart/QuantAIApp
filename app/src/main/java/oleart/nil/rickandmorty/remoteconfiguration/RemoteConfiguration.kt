package oleart.nil.rickandmorty.remoteconfiguration

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings.Builder
import javax.inject.Inject

class RemoteConfiguration @Inject constructor(
    private var context: Context,
    private var firebaseRemoteConfig: FirebaseRemoteConfig
) {

    companion object {

        const val OPEN_AI_KEY = "open_ai_key"
    }

    fun getOpenAIKey(): String {
        val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        val bundle: Bundle = Bundle()
        bundle.putString("test_param", "test_value")
        firebaseAnalytics.logEvent("test_event", bundle)

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

        val key = firebaseRemoteConfig.getString(OPEN_AI_KEY)
        return key
    }
}