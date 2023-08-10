package guru.springframework.Springrestmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.Springrestmvc.entities.Beer;
import guru.springframework.Springrestmvc.mappers.BeerMapper;
import guru.springframework.Springrestmvc.model.BeerDTO;
import guru.springframework.Springrestmvc.repositories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testPatchBeerBadName() throws Exception {

        Beer beer=beerRepository.findAll().get(0);

        Map<String,Object> beerMap = new HashMap<>();
        beerMap.put("beerName","New Name 86492368989704279097402740972305972095709237597203740397509237975975097507230590327509248632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359864923689486325986235986492368948632598623598649236894863259862359");

        MvcResult result= mockMvc.perform(patch(BeerController.BEER_PATH_ID , beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    void testDeleteByIdNotFound(){
        assertThrows(NotFoundException.class, ()->{
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound(){
        Beer beer= beerRepository.findAll().get(0);
        ResponseEntity responseEntity=beerController.deleteById(beer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound(){
        assertThrows(NotFoundException.class , ()->{
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingBeer(){
        Beer beer=beerRepository.findAll().get(0);
        BeerDTO beerDTO=beerMapper.beertoBeerDto(beer);

        beerDTO.setId(null);
        beerDTO.setVersion(null);

        final String beerName ="UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity=beerController.updateById(beer.getId(), beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();

        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest(){
        BeerDTO beerDTO= BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity responseEntity=beerController.handlePost(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");

        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Beer beer=beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();

    }

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Beer beer=beerRepository.findAll().get(0);

        BeerDTO  dto=beerController.getBeerById(beer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testListBeers() {
        List<BeerDTO> dtos = beerController.listBeers();

        assertThat(dtos.size()).isEqualTo(2413);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerDTO> dtos=beerController.listBeers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}