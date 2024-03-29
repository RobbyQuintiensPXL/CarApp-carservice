package be.carshop.carservice.repository;

import be.carshop.carservice.dto.BrandDto;
import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class BrandRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BrandRepository brandRepository;

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
        entityManager.flush();
    }

    @Test
    public void showAllBrandsTest() {
        List<BrandDto> brandDtoList = brandRepository.findAll().stream().map(BrandDto::new).collect(Collectors.toList());

        assertThat(brandDtoList).isNotEmpty();
        assertThat(brandDtoList.get(0).getBrand()).isEqualTo(brand.getBrandName());
        assertThat(brandDtoList.get(0).getCountry().getCountryName()).isEqualTo(brand.getCountry().getCountryName());
    }

    @Test
    public void showBrandsByCountryTest() {
        List<BrandDto> brandDtoList = brandRepository.findAllByCountry_CountryName(country.getCountryName()).stream().map(BrandDto::new).collect(Collectors.toList());

        assertThat(brandDtoList).isNotEmpty();
        assertThat(brandDtoList.get(0).getLogoUrl()).isEqualTo(brand.getLogoUrl());
        assertThat(brandDtoList.get(0).getCountry().getCountryName()).isEqualTo(brand.getCountry().getCountryName());
    }

    @Test
    public void showBrandByBrandNameTest() {
        BrandDto brandDto = brandRepository.findByBrandName(brand.getBrandName()).map(BrandDto::new).get();

        assertThat(brandDto).isNotNull();
        assertThat(brandDto.getUrl()).isEqualTo(brand.getUrl());
    }
}
