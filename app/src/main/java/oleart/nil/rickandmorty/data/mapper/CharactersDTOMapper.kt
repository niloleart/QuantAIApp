package oleart.nil.rickandmorty.data.mapper

import oleart.nil.rickandmorty.base.Mapper
import oleart.nil.rickandmorty.data.model.network.CharactersResponseDTO
import oleart.nil.rickandmorty.domain.model.Characters

class CharactersDTOMapper : Mapper<CharactersResponseDTO, Characters>() {

    override fun transformTo(charactersResponseDTO: CharactersResponseDTO) = Characters(
        InfoDTOMapper().transformTo(charactersResponseDTO.info),
        CharacterDTOMapper().transformTo(charactersResponseDTO.results).toMutableList()
    )
}