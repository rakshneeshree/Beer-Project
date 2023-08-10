package guru.springframework.Springrestmvc.repositories;

import guru.springframework.Springrestmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
