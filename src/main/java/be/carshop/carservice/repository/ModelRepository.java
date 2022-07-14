package be.carshop.carservice.repository;

import be.carshop.carservice.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {

    List<Model> findAllByBrand_BrandName(String brand);

    Optional<Model> findByModelName(String model);
}
