package guru.springframework.Springrestmvc.controller;

import guru.springframework.Springrestmvc.entities.Customer;
import guru.springframework.Springrestmvc.mappers.CustomerMapper;
import guru.springframework.Springrestmvc.model.CustomerDTO;
import guru.springframework.Springrestmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () ->{
            customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build());

        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Customer customer=customerRepository.findAll().get(0);
        ResponseEntity responseEntity= customerController.deleteById(customer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () ->{
            customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build());

        });
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingBeer() {
        Customer customer=customerRepository.findAll().get(0);
        CustomerDTO customerDTO=customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);

        final String customername = "UPDATED";
        customerDTO.setCustomername(customername);

        ResponseEntity responseEntity= customerController.updateById(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer= customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getCustomername()).isEqualTo(customername);
    }

    @Rollback
    @Transactional
    @Test
    void saveNewCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customername("New Customer")
                .build();

        ResponseEntity responseEntity=customerController.handlePost(customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[3]);

        Customer customer= customerRepository.findById(savedUUID).get();

        assertThat(customer).isNotNull();
    }

    @Test
    void testCustomerIdNotFound() {

        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());

        });
    }

    @Test
    void testGetById() {
        Customer customer=customerRepository.findAll().get(0);

        CustomerDTO dto=customerController.getCustomerById(customer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> dtos=customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(2);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos=customerController.listCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

}