package guru.springframework.Springrestmvc.repositories;

import guru.springframework.Springrestmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
