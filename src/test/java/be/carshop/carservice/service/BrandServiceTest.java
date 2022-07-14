package be.carshop.carservice.service;
import be.carshop.carservice.dto.BrandDto;
import be.carshop.carservice.dto.CountryDto;
import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
import be.carshop.carservice.repository.BrandRepository;
import be.carshop.carservice.repository.CountryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class BrandServiceTest {

    @MockBean
    private BrandRepository brandRepository;

    @Autowired
    private BrandService brandService;

    private Country country;
    private Brand brand;
    private List<Brand> brandList;

    @Before
    public void init() {
        country = new Country();
        country.setCountry("TestCountry");
        brand = new Brand();
        brand.setBrand("TestBrand");
        brand.setUrl("TestUrl");
        brand.setLogoUrl("TestLogoUrl");
        brand.setCountry(country);
        brandList = new LinkedList<>();
        brandList.add(brand);
    }

    @Test
    public void showAllBrandsTest() {
        when(brandRepository.findAll()).thenReturn(brandList);

        List<BrandDto> brandDtoList = brandService.getAllBrands();

        assertEquals(brandDtoList.get(0).getCountry().getCountry(), brand.getCountry().getCountry());
        assertEquals(brandDtoList.get(0).getBrand(), brand.getBrand());
        assertEquals(brandDtoList.get(0).getLogoUrl(), brand.getLogoUrl());
        assertEquals(brandDtoList.get(0).getUrl(), brand.getUrl());
    }

    @Test
    public void showAllBrandsByCountryTest() {
        when(brandRepository.findAllByCountry_Country(country.getCountry())).thenReturn(brandList);

        List<BrandDto> brandDtoList = brandService.getAllBrandsByCountry(country.getCountry());

        assertEquals(brandDtoList.get(0).getCountry().getCountry(), brand.getCountry().getCountry());
        assertEquals(brandDtoList.get(0).getBrand(), brand.getBrand());
        assertEquals(brandDtoList.get(0).getLogoUrl(), brand.getLogoUrl());
        assertEquals(brandDtoList.get(0).getUrl(), brand.getUrl());
    }

    @Test
    public void showBrandByBrandNameTest() {
        when(brandRepository.findByBrand(brand.getBrand())).thenReturn(Optional.ofNullable(brand));

        BrandDto brandDto = brandService.getBrandByBrandName(brand.getBrand());

        assertEquals(brandDto.getCountry().getCountry(), brand.getCountry().getCountry());
        assertEquals(brandDto.getBrand(), brand.getBrand());
        assertEquals(brandDto.getLogoUrl(), brand.getLogoUrl());
        assertEquals(brandDto.getUrl(), brand.getUrl());
    }
}
