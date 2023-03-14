package oleart.nil.rickandmorty.base.errors

class ServerErrorException(error: RickAndMortyError) : RickAndMortyException(error) {
}