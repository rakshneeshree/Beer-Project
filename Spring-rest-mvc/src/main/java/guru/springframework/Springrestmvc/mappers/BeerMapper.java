package guru.springframework.Springrestmvc.mappers;

import guru.springframework.Springrestmvc.entities.Beer;
import guru.springframework.Springrestmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer BeerDtoToBeer(BeerDTO dto);

    BeerDTO beertoBeerDto(Beer beer);
}
