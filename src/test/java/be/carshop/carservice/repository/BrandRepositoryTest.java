package be.carshop.carservice.repository;

import be.carshop.carservice.dto.BrandDto;
import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
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
public class BrandRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BrandRepository brandRepository;

    private Brand brand;
    private Country country;

    public void persist() {
        country = new Country();
        country.setCountry("TestCountry");
        entityManager.persist(country);
        brand = new Brand();
        brand.setBrand("TestBrand");
        brand.setCountry(country);
        brand.setUrl("www.testbrand.com");
        brand.setLogoUrl("logourl.jpg");
        entityManager.persist(brand);
        entityManager.flush();
    }

    @Test
    public void showAllBrandsTest() {
        persist();

        List<BrandDto> brandDtoList = brandRepository.findAll().stream().map(BrandDto::new).collect(Collectors.toList());

        assertThat(brandDtoList).isNotEmpty();
        assertThat(brandDtoList.get(0).getBrand()).isEqualTo(brand.getBrand());
        assertThat(brandDtoList.get(0).getCountry().getCountry()).isEqualTo(brand.getCountry().getCountry());
    }

    @Test
    public void showBrandsByCountryTest() {
        persist();

        List<BrandDto> brandDtoList = brandRepository.findAllByCountry(country).stream().map(BrandDto::new).collect(Collectors.toList());

        assertThat(brandDtoList).isNotEmpty();
        assertThat(brandDtoList.get(0).getLogoUrl()).isEqualTo(brand.getLogoUrl());
        assertThat(brandDtoList.get(0).getCountry().getCountry()).isEqualTo(brand.getCountry().getCountry());
    }

    @Test
    public void showBrandByBrandNameTest() {
        persist();

        BrandDto brandDto = brandRepository.findByBrand(brand.getBrand()).map(BrandDto::new).get();

        assertThat(brandDto).isNotNull();
        assertThat(brandDto.getUrl()).isEqualTo(brand.getUrl());
    }
}
