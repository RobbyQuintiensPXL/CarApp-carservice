package be.carshop.carservice.repository;

import be.carshop.carservice.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {

    List<Model> findAllByBrand_BrandName(String brand);
    List<Model> findAllByBrand_Id(Long id);

    Optional<Model> findByModelName(String model);

    Optional<Model> findByModelNameAndBrand_BrandName(String model, String brand);
}
