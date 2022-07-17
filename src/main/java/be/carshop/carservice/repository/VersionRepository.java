package be.carshop.carservice.repository;

import be.carshop.carservice.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long> {

    List<Version> findAllByModel_ModelName(String model);

    Optional<Version> findByVersionName(String version);
}
