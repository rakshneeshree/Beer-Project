package guru.springframework.Springrestmvc.mappers;

import guru.springframework.Springrestmvc.entities.Customer;
import guru.springframework.Springrestmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer CustomerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
