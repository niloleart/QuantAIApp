package oleart.nil.rickandmorty.data.model.network

import com.google.gson.annotations.SerializedName

data class InfoDTO(
    @SerializedName("count")
    val countDTO: Int,

    @SerializedName("pages")
    val pagesDTO: Int,

    @SerializedName("next")
    val nextDTO: String,

    @SerializedName("prev")
    val prevDTO: String?
)