package oleart.nil.rickandmorty.data.api

import oleart.nil.rickandmorty.data.model.network.CharactersResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RickAndMortyApi {

    @GET
    fun getCharacters(
        @Url url: String
    ): Call<CharactersResponseDTO>

    @GET
    fun getCharacters(
        @Url url: String,
        @Query("page") request: Int
    ): Call<CharactersResponseDTO>
}