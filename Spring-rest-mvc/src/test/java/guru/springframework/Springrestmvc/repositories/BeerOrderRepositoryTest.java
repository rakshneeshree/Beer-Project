package guru.springframework.Springrestmvc.repositories;

import guru.springframework.Springrestmvc.entities.Beer;
import guru.springframework.Springrestmvc.entities.BeerOrder;
import guru.springframework.Springrestmvc.entities.BeerOrderShipment;
import guru.springframework.Springrestmvc.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp(){
        testCustomer = customerRepository.findAll().get(0);
        testBeer=beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testBeerOrder(){
        BeerOrder beerOrder= BeerOrder.builder()
                .customerRef("Test order")
                .customer(testCustomer)
                .beerOrderShipment(BeerOrderShipment.builder()
                        .trackingNumber("1234r")
                        .build())
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);

        System.out.println(savedBeerOrder.getCustomerRef());
        //System.out.println(beerOrderRepository.count());
        //System.out.println(customerRepository.count());
        //System.out.println(beerRepository.count());
        //System.out.println(testCustomer.getCustomername());
        //System.out.println(testBeer.getBeerName());
    }

}