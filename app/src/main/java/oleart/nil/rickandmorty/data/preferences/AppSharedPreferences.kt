package oleart.nil.rickandmorty.data.preferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(context: Context) {

    companion object {

        private const val PREFERENCES_APP = "PREFERENCES_APP_SESSION"
        private const val LAST_LOADED_SCREEN = "last_loaded_screen"
    }

    private var preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(PREFERENCES_APP, Context.MODE_PRIVATE)
    }

    fun setLastLoadedPage(lastPage: Int) {
        preferences.edit().putInt(LAST_LOADED_SCREEN, lastPage).apply()
    }

    fun getLastLoadedPage(): Int = preferences.getInt(LAST_LOADED_SCREEN, 1)
}