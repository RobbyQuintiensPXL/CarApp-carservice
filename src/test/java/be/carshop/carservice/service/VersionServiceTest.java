package be.carshop.carservice.service;

import be.carshop.carservice.dto.VersionDto;
import be.carshop.carservice.model.*;
import be.carshop.carservice.repository.VersionRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VersionServiceTest {

    @MockBean
    private VersionRepository versionRepository;

    @Autowired
    private VersionService versionService;

    private Version version;
    private Country country;
    private Brand brand;
    private Model model;
    private List<Version> versionList;

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
        model.setFuelType(FuelType.DIESEL);
        model.setBrand(brand);
        version = new Version();
        version.setModel(model);
        version.setEmission(Emission.EURO_6);
        version.setCylinder(2000);
        version.setCo2(100);
        version.setVersionName("VersionName");
        versionList = new LinkedList<>();
        versionList.add(version);
    }

    @Test
    public void showAllVersionsTest() {
        when(versionRepository.findAll()).thenReturn(versionList);

        List<VersionDto> versionDtoList = versionService.getAllVersions();

        assertEquals(versionDtoList.get(0).getModel().getModelName(), version.getModel().getModelName());
        assertEquals(versionDtoList.get(0).getCo2(), version.getCo2());
        assertEquals(versionDtoList.get(0).getCylinder(), version.getCylinder());
    }

    @Test
    public void showAllVersionsByModelTest() {
        when(versionRepository.findAllByModel_ModelName(anyString())).thenReturn(versionList);

        List<VersionDto> versionDtoList = versionService.getAllVersionsByModel(model.getModelName());

        assertEquals(versionDtoList.get(0).getModel().getModelName(), version.getModel().getModelName());
        assertEquals(versionDtoList.get(0).getCo2(), version.getCo2());
        assertEquals(versionDtoList.get(0).getCylinder(), version.getCylinder());
        assertEquals(versionDtoList.get(0).getModel().getFuelType(),
                version.getModel().getFuelType());
    }

    @Test
    public void showVersionByNameTest() {
        when(versionRepository.findByVersionName(anyString())).thenReturn(Optional.ofNullable(version));

        VersionDto versionDto = versionService.getVersionByName(model.getModelName());

        assertEquals(versionDto.getModel().getModelName(), version.getModel().getModelName());
        assertEquals(versionDto.getModel().getBrand().getBrandName(),
                version.getModel().getBrand().getBrandName());
    }
}
