package guru.springframework.Springrestmvc.bootstrap;

import guru.springframework.Springrestmvc.repositories.BeerRepository;
import guru.springframework.Springrestmvc.services.BeerCsvService;
import guru.springframework.Springrestmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(BeerCsvServiceImpl.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerCsvService csvService;


    BootstrapData bootstrapData;

    @BeforeEach
    void setUp(){
        bootstrapData = new BootstrapData(beerRepository,csvService);
    }

    @Test
    void run() throws Exception {

        bootstrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
    }
}