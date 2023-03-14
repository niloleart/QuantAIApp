package oleart.nil.rickandmorty.data.model.network

import com.google.gson.annotations.SerializedName
import oleart.nil.rickandmorty.data.model.network.base.BaseResponseDTO

data class CharactersResponseDTO(
    @SerializedName("info")
    val info: InfoDTO,
    @SerializedName("results")
    val results: List<CharacterDTO>
) : BaseResponseDTO() {
}