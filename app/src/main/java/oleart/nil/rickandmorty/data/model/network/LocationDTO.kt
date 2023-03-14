package oleart.nil.rickandmorty.data.model.network

import com.google.gson.annotations.SerializedName

data class LocationDTO(
    @SerializedName("name")
    val nameDTO: String,

    @SerializedName("url")
    val urlDTO: String
)