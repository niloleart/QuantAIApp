package oleart.nil.rickandmorty.base.errors

open class RickAndMortyException(error: RickAndMortyError) : Exception() {

    private val error: RickAndMortyError
    fun getError(): RickAndMortyError {
        return error
    }

    init {
        this.error = error
    }
}