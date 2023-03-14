package oleart.nil.rickandmorty.data.mapper

import oleart.nil.rickandmorty.base.Mapper
import oleart.nil.rickandmorty.data.model.network.LocationDTO
import oleart.nil.rickandmorty.domain.model.Location

class LocationDTOMapper : Mapper<LocationDTO, Location>() {

    override fun transformTo(locationDTO: LocationDTO) = Location(
        locationDTO.nameDTO, locationDTO.urlDTO
    )
}