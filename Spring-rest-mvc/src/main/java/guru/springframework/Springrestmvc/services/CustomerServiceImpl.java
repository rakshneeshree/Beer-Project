package guru.springframework.Springrestmvc.services;

import guru.springframework.Springrestmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j

public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        this.customerMap = new HashMap<>();

        CustomerDTO cust1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customername("Rakshnee ")
                .version(1)
                .createddate(LocalDateTime.now())
                .lastmodifieddate(LocalDateTime.now())
                .build();

        CustomerDTO cust2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customername("John")
                .version(2)
                .createddate(LocalDateTime.now())
                .lastmodifieddate(LocalDateTime.now())
                .build();

        customerMap.put(cust1.getId(), cust1);
        customerMap.put(cust2.getId(), cust2);
    }
    @Override
    public Optional<CustomerDTO> updateById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existing = customerMap.get(customerId);
        existing.setCustomername(customer.getCustomername());

        return Optional.of(existing);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        log.debug("Get Customer by ID is in service " + id.toString());

        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customername(customer.getCustomername())
                .version(customer.getVersion())
                .createddate(LocalDateTime.now())
                .lastmodifieddate(LocalDateTime.now())
                .build();

        customerMap.put(savedCustomer.getId(),savedCustomer);
        return savedCustomer;
    }

    @Override
    public Boolean deleteById(UUID customerId) {
        customerMap.remove(customerId);
        return true;
    }

    @Override
    public void patchById(UUID customerId, CustomerDTO customer) {

        CustomerDTO existing = customerMap.get(customerId);

        if(StringUtils.hasText(customer.getCustomername())){
            existing.setCustomername(customer.getCustomername());
        }
    }
}
