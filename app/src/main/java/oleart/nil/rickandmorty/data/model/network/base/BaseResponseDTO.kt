package oleart.nil.rickandmorty.data.model.network.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponseDTO {
    // TODO

    @SerializedName("Error")
    @Expose
    var error: ErrorDTO? = null
}