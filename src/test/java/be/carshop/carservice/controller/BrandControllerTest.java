package be.carshop.carservice.controller;

import be.carshop.carservice.dto.BrandDto;
import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
import be.carshop.carservice.service.BrandService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class BrandControllerTest {

    @InjectMocks
    BrandController brandController;

    @Mock
    BrandService brandService;

    private Brand brand;
    private Country country;
    private List<Brand> brandList;

    @Before
    public void init() {
        country = new Country();
        country.setId(1L);
        country.setCountryName("TestCountry");
        brand = new Brand();
        brand.setId(1L);
        brand.setBrandName("TestBrand");
        brand.setUrl("TestUrl");
        brand.setLogoUrl("TestLogoUrl");
        brand.setCountry(country);
        brandList = new LinkedList<>();
        brandList.add(brand);
    }

    @Test
    public void getAllBrandsTest() {
        when(brandService.getAllBrands()).thenReturn(brandList.stream().map(BrandDto::new).collect(Collectors.toList()));

        ResponseEntity<List<BrandDto>> responseEntity = brandController.getAllBrands();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}
