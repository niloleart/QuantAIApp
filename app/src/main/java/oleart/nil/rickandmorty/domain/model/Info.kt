package oleart.nil.rickandmorty.domain.model

import java.io.Serializable

data class Info(
    val count: Int,
    val pages: Int,
    val nextPage: String,
    val prevPage: String?
) : Serializable