package guru.springframework.Springrestmvc.services;

import guru.springframework.Springrestmvc.model.BeerDTO;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {


    List<BeerDTO> listBeers();

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateById(UUID beerId, BeerDTO beer);

    boolean deleteById(UUID beerId);

    Optional<BeerDTO> patchById(UUID beerId, BeerDTO beer);
}
