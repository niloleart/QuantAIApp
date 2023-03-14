package oleart.nil.rickandmorty.data.mapper

import oleart.nil.rickandmorty.base.Mapper
import oleart.nil.rickandmorty.data.model.network.CharacterDTO
import oleart.nil.rickandmorty.domain.model.Character

class CharacterDTOMapper : Mapper<CharacterDTO, Character>() {

    override fun transformTo(entities: List<CharacterDTO>): List<Character> = entities.map {
        Character(
            it.idDTO,
            it.nameDTO,
            it.statusDTO,
            it.speciesDTO,
            geta(it.typeDTO),
            it.genderDTO,
            LocationDTOMapper().transformTo(it.originDTO),
            LocationDTOMapper().transformTo(it.locationDTO),
            it.image,
            it.episodeDTO,
            it.urlDTO,
            it.createdDTO
        )
    }

    private fun geta(typeDTO: String?) = if (typeDTO.isNullOrBlank()) "" else typeDTO
}