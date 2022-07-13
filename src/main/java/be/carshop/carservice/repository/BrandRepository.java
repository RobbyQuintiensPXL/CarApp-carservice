package be.carshop.carservice.repository;

import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByBrand(String brand);

    List<Brand> findAllByCountry(Country country);
}
