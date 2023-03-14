package oleart.nil.rickandmorty.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import oleart.nil.rickandmorty.base.Either
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.data.datasource.RickAndMortyNDS
import oleart.nil.rickandmorty.domain.model.Characters
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val context: Context,
    private val rickAndMortyNDS: RickAndMortyNDS
) {

    suspend fun getAllCharacters(page: Int = 1): Either<DataSourceError, Characters> =
        withContext(Dispatchers.IO) {
            rickAndMortyNDS.getData(page)
        }
}