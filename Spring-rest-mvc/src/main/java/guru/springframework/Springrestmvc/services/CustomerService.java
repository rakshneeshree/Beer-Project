package guru.springframework.Springrestmvc.services;

import guru.springframework.Springrestmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {



    Optional<CustomerDTO> updateById(UUID customerId, CustomerDTO customer);

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    Boolean deleteById(UUID customerId);


    void patchById(UUID customerId, CustomerDTO customer);
}