package be.carshop.carservice.service;

import be.carshop.carservice.dto.ModelDto;
import be.carshop.carservice.exception.BusinessException;
import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
import be.carshop.carservice.model.FuelType;
import be.carshop.carservice.model.Model;
import be.carshop.carservice.repository.ModelRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
        model.setBrand(brand);
        modelList = new LinkedList<>();
        modelList.add(model);
    }

    @Test
    public void showAllModelsTest() {
        when(modelRepository.findAll()).thenReturn(modelList);

        List<ModelDto> modelDtoList = modelService.getAllModels();

        assertEquals(modelDtoList.get(0).getModel(), model.getModelName());
        assertEquals(modelDtoList.get(0).getBrand(),
                model.getBrand().getBrandName());
    }

    @Test
    public void showAllModelsByBrandTest() {
        when(modelRepository.findAllByBrand_Id(brand.getId()))
                .thenReturn(modelList);

        List<ModelDto> modelDtoList = modelService.getAllModelsByBrand(brand.getId());

        assertEquals(modelDtoList.get(0).getModel(), model.getModelName());
        assertEquals(modelDtoList.get(0).getBrand(), model.getBrand().getBrandName());
    }

    @Test
    public void showModelByModelName() {
        when(modelRepository.findByModelName(model.getModelName()))
                .thenReturn(Optional.ofNullable(model));

        ModelDto modelDto = modelService.getModelByModelName(model.getModelName());

        assertEquals(modelDto.getBrand(),
                model.getBrand().getBrandName());
        assertEquals(modelDto.getModel(), model.getModelName());
    }

    @Test
    public void showModelById() {
        when(modelRepository.findById(model.getId()))
                .thenReturn(Optional.ofNullable(model));

        ModelDto modelDto = modelService.getModelById(model.getId());

        assertEquals(modelDto.getBrand(),
                model.getBrand().getBrandName());
        assertEquals(modelDto.getModel(), model.getModelName());
    }

    @Test(expected = BusinessException.class)
    public void throwExceptionNoModelsFound() {
        ModelService modelSpy = Mockito.spy(modelService);
        when(modelSpy.getAllModels()).thenThrow(BusinessException.class);

        modelSpy.getAllModels();
    }

    @Test(expected = BusinessException.class)
    public void throwExceptionNoModelsByBrandFound() {
        ModelService modelSpy = Mockito.spy(modelService);
        when(modelSpy.getAllModelsByBrand(anyLong())).thenThrow(BusinessException.class);

        modelSpy.getAllModelsByBrand(Long.valueOf("Id"));
    }

    @Test(expected = BusinessException.class)
    public void throwExceptionNoModelByNameFound() {
        ModelService modelSpy = Mockito.spy(modelService);
        when(modelSpy.getModelByModelName(anyString())).thenThrow(BusinessException.class);

        modelSpy.getModelByModelName("Model");
    }

    @Test(expected = BusinessException.class)
    public void throwExceptionNoModelByIdFound() {
        ModelService modelSpy = Mockito.spy(modelService);
        when(modelSpy.getModelById(anyLong())).thenThrow(BusinessException.class);

        modelSpy.getModelById(1L);
    }
}
