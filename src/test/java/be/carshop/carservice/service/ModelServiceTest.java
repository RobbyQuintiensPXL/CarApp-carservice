package be.carshop.carservice.service;
import be.carshop.carservice.dto.BrandDto;
import be.carshop.carservice.dto.ModelDto;
import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
import be.carshop.carservice.model.FuelType;
import be.carshop.carservice.model.Model;
import be.carshop.carservice.repository.BrandRepository;
import be.carshop.carservice.repository.ModelRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ModelServiceTest {

    @MockBean
    private ModelRepository modelRepository;

    @Autowired
    private ModelService modelService;

    private Country country;
    private Brand brand;
    private Model model;
    private List<Model> modelList;

    @Before
    public void init() {
        country = new Country();
        country.setCountryName("TestCountry");
        brand = new Brand();
        brand.setBrandName("TestBrand");
        brand.setUrl("TestUrl");
        brand.setLogoUrl("TestLogoUrl");
        brand.setCountry(country);
        model = new Model();
        model.setModelName("TestModel");
        model.setImageUrl("imageUrl");
        model.setFuelType(FuelType.DIESEL);
        model.setBrand(brand);
        model.setBrand(brand);
        modelList = new LinkedList<>();
        modelList.add(model);
    }

    @Test
    public void showAllModelsTest() {
        when(modelRepository.findAll()).thenReturn(modelList);

        List<ModelDto> modelDtoList = modelService.getAllModels();

        assertEquals(modelDtoList.get(0).getModel(), model.getModelName());
        assertEquals(modelDtoList.get(0).getFuelType(), model.getFuelType());
        assertEquals(modelDtoList.get(0).getImageUrl(), model.getImageUrl());
        assertEquals(modelDtoList.get(0).getBrand().getBrandName(),
                model.getBrand().getBrandName());
    }

    @Test
    public void showAllModelsByBrandTest() {
        when(modelRepository.findAllByBrand_BrandName(brand.getBrandName()))
                .thenReturn(modelList);

        List<ModelDto> modelDtoList = modelService.getAllModelsByBrand(brand.getBrandName());

        assertEquals(modelDtoList.get(0).getModel(), model.getModelName());
        assertEquals(modelDtoList.get(0).getFuelType(), model.getFuelType());
        assertEquals(modelDtoList.get(0).getImageUrl(), model.getImageUrl());
        assertEquals(modelDtoList.get(0).getBrand().getBrandName(), model.getBrand().getBrandName());
    }
}
