package oleart.nil.rickandmorty.data.datasource

import oleart.nil.rickandmorty.base.errors.RickAndMortyError
import oleart.nil.rickandmorty.base.errors.RickAndMortyException
import oleart.nil.rickandmorty.base.errors.ServerErrorException
import retrofit2.Call
import java.io.IOException

abstract class NDS<RequestType, ResultType> : DataSource<RequestType, ResultType> {

    @Throws(RickAndMortyException::class)
    fun <ResultTypeDTO> makeCall(call: Call<ResultTypeDTO>) {
        try {
            call.execute()
        } catch (e: IOException) {
            throw ServerErrorException(RickAndMortyError())
        }
    }

    @Throws(RickAndMortyException::class)
    fun <ResultTypeDTO> makeCallWithResult(call: Call<ResultTypeDTO>): ResultTypeDTO {
        try {
            return call.execute().body()!!
        } catch (e: IOException) {
            throw ServerErrorException(RickAndMortyError())
        }
    }
}