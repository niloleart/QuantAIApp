package oleart.nil.rickandmorty.domain.model

import java.io.Serializable

data class Characters(
    var info: Info,
    var characters: MutableList<Character>
) : Serializable