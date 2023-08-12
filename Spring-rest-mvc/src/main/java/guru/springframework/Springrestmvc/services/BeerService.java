package guru.springframework.Springrestmvc.services;

import guru.springframework.Springrestmvc.model.BeerDTO;
import guru.springframework.Springrestmvc.model.BeerStyle;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {


    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateById(UUID beerId, BeerDTO beer);

    boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchById(UUID beerId, BeerDTO beer);
}
