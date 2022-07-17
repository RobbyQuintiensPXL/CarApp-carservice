package be.carshop.carservice.repository;

import be.carshop.carservice.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByBrandName(String brand);

    List<Brand> findAllByCountry_CountryName(String country);
}
