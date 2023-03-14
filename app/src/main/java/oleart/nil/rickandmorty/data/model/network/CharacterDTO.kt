package oleart.nil.rickandmorty.data.model.network

import com.google.gson.annotations.SerializedName

data class CharacterDTO(
    @SerializedName("id")
    val idDTO: Int,

    @SerializedName("name")
    val nameDTO: String,

    @SerializedName("status")
    val statusDTO: String,

    @SerializedName("species")
    val speciesDTO: String,

    @SerializedName("type")
    val typeDTO: String?,

    @SerializedName("gender")
    val genderDTO: String,

    @SerializedName("origin")
    val originDTO: LocationDTO,

    @SerializedName("location")
    val locationDTO: LocationDTO,

    @SerializedName("image")
    val image: String,

    @SerializedName("episode")
    val episodeDTO: List<String>,

    @SerializedName("url")
    val urlDTO: String,

    @SerializedName("created")
    val createdDTO: String //TODO date

)