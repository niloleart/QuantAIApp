package oleart.nil.rickandmorty.data.mapper

import oleart.nil.rickandmorty.base.Mapper
import oleart.nil.rickandmorty.data.model.network.InfoDTO
import oleart.nil.rickandmorty.domain.model.Info

class InfoDTOMapper : Mapper<InfoDTO, Info>() {

    override fun transformTo(infoDTO: InfoDTO) = with(infoDTO) {
        Info(this.countDTO, this.pagesDTO, this.nextDTO, this.prevDTO)
    }
}