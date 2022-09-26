package be.carshop.carservice.repository;

import be.carshop.carservice.dto.ModelDto;
import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
import be.carshop.carservice.model.Model;
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
public class ModelRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelRepository modelRepository;

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
        entityManager.persist(model);
        entityManager.flush();
    }

    @Test
    public void showAllModelsTest() {
        List<ModelDto> modelDtoList = modelRepository.findAll().stream().map(ModelDto::new)
                .collect(Collectors.toList());

        assertThat(modelDtoList).isNotEmpty();
        assertThat(modelDtoList.get(0).getModel()).isEqualTo(model.getModelName());
    }

    @Test
    public void showModelsByBrandTest() {
        List<ModelDto> modelDtoList = modelRepository.findAllByBrand_BrandName(brand.getBrandName())
                .stream().map(ModelDto::new).collect(Collectors.toList());

        assertThat(modelDtoList).isNotEmpty();
        assertThat(modelDtoList.get(0).getModel()).isEqualTo(model.getModelName());
    }

    @Test
    public void showModelByModelName() {
        Model optionalModel = modelRepository.findByModelName(model.getModelName()).get();

        assertThat(optionalModel).isNotNull();
        assertThat(optionalModel.getBrand().getBrandName())
                .isEqualTo(model.getBrand().getBrandName());
        assertThat(optionalModel.getBrand().getUrl()).isEqualTo(model.getBrand().getUrl());
        assertThat(optionalModel.getBrand().getCountry().getCountryName())
                .isEqualTo(model.getBrand().getCountry().getCountryName());
    }
}
