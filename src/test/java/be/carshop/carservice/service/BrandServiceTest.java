package be.carshop.carservice.service;

import be.carshop.carservice.dto.BrandDto;
import be.carshop.carservice.exception.BusinessException;
import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
import be.carshop.carservice.repository.BrandRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
        country.setCountryName("TestCountry");
        brand = new Brand();
        brand.setBrandName("TestBrand");
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

        assertEquals(brandDtoList.get(0).getCountry().getCountryName(),
                brand.getCountry().getCountryName());
        assertEquals(brandDtoList.get(0).getBrand(), brand.getBrandName());
        assertEquals(brandDtoList.get(0).getLogoUrl(), brand.getLogoUrl());
        assertEquals(brandDtoList.get(0).getUrl(), brand.getUrl());
    }

    @Test
    public void showAllBrandsByCountryTest() {
        when(brandRepository.findAllByCountry_CountryName(country.getCountryName()))
                .thenReturn(brandList);

        List<BrandDto> brandDtoList = brandService.getAllBrandsByCountry(country.getCountryName());

        assertEquals(brandDtoList.get(0).getCountry().getCountryName(),
                brand.getCountry().getCountryName());
        assertEquals(brandDtoList.get(0).getBrand(), brand.getBrandName());
        assertEquals(brandDtoList.get(0).getLogoUrl(), brand.getLogoUrl());
        assertEquals(brandDtoList.get(0).getUrl(), brand.getUrl());
    }

    @Test
    public void showBrandByBrandNameTest() {
        when(brandRepository.findByBrandName(brand.getBrandName()))
                .thenReturn(Optional.ofNullable(brand));

        BrandDto brandDto = brandService.getBrandByBrandName(brand.getBrandName());

        assertEquals(brandDto.getCountry().getCountryName(),
                brand.getCountry().getCountryName());
        assertEquals(brandDto.getBrand(), brand.getBrandName());
        assertEquals(brandDto.getLogoUrl(), brand.getLogoUrl());
        assertEquals(brandDto.getUrl(), brand.getUrl());
    }

    @Test(expected = BusinessException.class)
    public void throwExceptionNoBrandsFound() {
        BrandService brandSpy = Mockito.spy(brandService);
        when(brandSpy.getAllBrands()).thenThrow(BusinessException.class);

        brandSpy.getAllBrands();
    }

    @Test(expected = BusinessException.class)
    public void throwExceptionNoBrandsByCountryFound() {
        BrandService brandSpy = Mockito.spy(brandService);
        when(brandSpy.getAllBrandsByCountry(anyString())).thenThrow(BusinessException.class);

        brandSpy.getAllBrandsByCountry("Country");
    }

    @Test(expected = BusinessException.class)
    public void throwExceptionNoBrandByNameFound() {
        BrandService brandSpy = Mockito.spy(brandService);
        when(brandSpy.getBrandByBrandName(anyString())).thenThrow(BusinessException.class);

        brandSpy.getBrandByBrandName("Brand");
    }
}
