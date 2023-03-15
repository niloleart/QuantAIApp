package oleart.nil.rickandmorty.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import oleart.nil.rickandmorty.db.AppDatabase
import oleart.nil.rickandmorty.db.CharactersDao
import oleart.nil.rickandmorty.injection.scopes.PerApplication

@Module
class DBModule {

    @Provides
    @PerApplication
    fun providesAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-rickandmorty"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @PerApplication
    fun providesNetworkCallDao(appDatabase: AppDatabase): CharactersDao =
        appDatabase.charactersDao()
}