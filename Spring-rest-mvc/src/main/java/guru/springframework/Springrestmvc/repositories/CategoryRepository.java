package guru.springframework.Springrestmvc.repositories;

import guru.springframework.Springrestmvc.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
