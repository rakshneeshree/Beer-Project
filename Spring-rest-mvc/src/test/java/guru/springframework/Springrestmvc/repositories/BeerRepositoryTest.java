package guru.springframework.Springrestmvc.repositories;

import guru.springframework.Springrestmvc.bootstrap.BootstrapData;
import guru.springframework.Springrestmvc.entities.Beer;
import guru.springframework.Springrestmvc.model.BeerStyle;
import guru.springframework.Springrestmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName(){
        Page<Beer> list=beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);
        assertThat(list.getContent().size()).isEqualTo(336);
    }

    @Test
    void testSaveBeerTooLong(){

        assertThrows(ConstraintViolationException.class, ()->{
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My Beer refvhfakj86f98af9y8sfs6f98sa69f8s698f6sd98f6s9d86fd89s6f98ds6f9d6f89ds6a89tf9agfugisgfisdaf68asdf698sd6f98sajsdgfuydtsa7f65d7f5sd85f78s5f7s6f57s8d5f6ds57f6sdf75sd6f57s6d5f7sd5f7ds657f6ds576f57sd65fs6f98a698ds98f6ds89f6")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("2345321")
                    .price(new BigDecimal("12.34"))
                    .build());

            beerRepository.flush();
        });


    }

    @Test
    void testSaveBeer(){
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("My Beer")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("2345321")
                .price(new BigDecimal("12.34"))
                .build());

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}