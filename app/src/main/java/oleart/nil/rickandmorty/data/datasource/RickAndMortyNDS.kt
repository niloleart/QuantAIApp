package oleart.nil.rickandmorty.data.datasource

import android.content.Context
import oleart.nil.rickandmorty.base.Either
import oleart.nil.rickandmorty.base.errors.DataSourceError
import oleart.nil.rickandmorty.base.errors.RickAndMortyError
import oleart.nil.rickandmorty.base.errors.RickAndMortyException
import oleart.nil.rickandmorty.data.api.RickAndMortyApi
import oleart.nil.rickandmorty.data.mapper.CharactersDTOMapper
import oleart.nil.rickandmorty.data.retrofit.Api
import oleart.nil.rickandmorty.domain.model.Characters
import javax.inject.Inject

class RickAndMortyNDS @Inject constructor(
    val context: Context
) : NDS<Int, Either<DataSourceError, Characters>>() {

    override fun getData(request: Int): Either<DataSourceError, Characters> {
        // TODO: set urls in a file
        val url = "https://rickandmortyapi.com/api/character"
        val api = Api<RickAndMortyApi>().createApi(
            RickAndMortyApi::class.java, context, url = url
        )
        val call = api.getCharacters(url, request)
        return try {
            val responseDTO = makeCallWithResult(call)
            if (responseDTO.error == null) {
                Either.Success(CharactersDTOMapper().transformTo(responseDTO))
            } else {
                Either.Error(DataSourceError(RickAndMortyError())) // TODO parse errors
            }
        } catch (e: RickAndMortyException) {
            Either.Error(DataSourceError(RickAndMortyError()))
        }
    }
}