package oleart.nil.rickandmorty.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import oleart.nil.rickandmorty.data.RickAndMortyRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RickAndMortyInteractor @Inject constructor(
    private val repository: RickAndMortyRepository
) : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    suspend fun getAllCharacters() = repository.getAllCharacters()

    suspend fun getMoreCharacters(page: Int) = repository.getAllCharacters(page)
}