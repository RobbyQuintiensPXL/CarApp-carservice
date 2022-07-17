package be.carshop.carservice.repository;

import be.carshop.carservice.dto.VersionDto;
import be.carshop.carservice.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class VersionRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VersionRepository versionRepository;

    private Version version;
    private Model model;
    private Brand brand;
    private Country country;

    @Before
    public void persist() {
        country = new Country();
        country.setCountryName("TestCountry");
        entityManager.persist(country);
        brand = new Brand();
        brand.setBrandName("TestBrand");
        brand.setCountry(country);
        brand.setUrl("www.testbrand.com");
        brand.setLogoUrl("logourl.jpg");
        entityManager.persist(brand);
        model = new Model();
        model.setModelName("TestModel");
        model.setBrand(brand);
        model.setFuelType(FuelType.DIESEL);
        entityManager.persist(model);
        version = new Version();
        version.setModel(model);
        version.setCylinder(2000);
        version.setCo2(100);
        version.setEmission(Emission.EURO_6);
        version.setVersionName("modelVersion");
        entityManager.persist(version);
        entityManager.flush();
    }

    @Test
    public void showAllVersionsTest() {
        List<VersionDto> versionDtoList = versionRepository.findAll().stream().map(VersionDto::new)
                .collect(Collectors.toList());

        assertThat(versionDtoList).isNotEmpty();
        assertThat(versionDtoList.get(0).getModel().getModelName()).isEqualTo(version.getModel().getModelName());
    }

    @Test
    public void showAllVersionsByModelTest() {
        List<VersionDto> versionDtoList = versionRepository.findAllByModel_ModelName(model.getModelName())
                .stream().map(VersionDto::new).collect(Collectors.toList());

        assertThat(versionDtoList).isNotEmpty();
        assertThat(versionDtoList.get(0).getCo2()).isEqualTo(version.getCo2());
        assertThat(versionDtoList.get(0).getCylinder()).isEqualTo(version.getCylinder());
        assertThat(versionDtoList.get(0).getEmission()).isEqualTo(version.getEmission());
    }

    @Test
    public void showVersionByVersionName() {
        VersionDto versionDto = versionRepository.findByVersionName(version.getVersionName())
                .map(VersionDto::new).get();

        assertThat(versionDto.getVersionName()).isEqualTo(version.getVersionName());
        assertThat(versionDto.getModel().getBrand().getBrandName())
                .isEqualTo(version.getModel().getBrand().getBrandName());
    }

    @Test
    public void showVersionById() {
        VersionDto versionDto = versionRepository.findById(version.getId())
                .map(VersionDto::new).get();

        assertThat(versionDto.getVersionName()).isEqualTo(version.getVersionName());
        assertThat(versionDto.getModel().getBrand().getBrandName())
                .isEqualTo(version.getModel().getBrand().getBrandName());
    }
}
