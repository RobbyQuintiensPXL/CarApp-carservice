package be.carshop.carservice.repository;

import be.carshop.carservice.dto.ProductDto;
import be.carshop.carservice.model.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    Predicate predicate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;
    private Product product;
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
        entityManager.persist(model);
        version = new Version();
        version.setModel(model);
        version.setCylinder(2000);
        version.setCo2(100);
        version.setEmission(Emission.EURO_6);
        version.setVersionName("modelVersion");
        version.setFuelType(FuelType.DIESEL);
        entityManager.persist(version);
        product = new Product();
        product.setBrand(brand);
        product.setModel(model);
        product.setVersion(version);
        product.setPrice(10000);
        product.setFirstRegistration(LocalDate.now());
        product.setNumberDoors(5);
        product.setNumberKm(5000);
        product.setTransmission(Transmission.MANUEEL);
        entityManager.persist(product);
        entityManager.flush();
    }

    @Test
    public void showAllProductsTest() {
        Pageable paging = PageRequest.of(0, 5);
        Page<ProductDto> productList = productRepository.findAll(paging).map(ProductDto::new);

        List<ProductDto> productDtoList = productList.get().collect(Collectors.toList());

        assertThat(productDtoList).isNotEmpty();
        assertThat(productDtoList.get(0).getModel().getModel()).isEqualTo(product.getModel().getModelName());
        assertThat(productDtoList.get(0).getFirstRegistration()).isEqualTo(product.getFirstRegistration());
        assertThat(productDtoList.get(0).getNumberDoors()).isEqualTo(product.getNumberDoors());
    }

    @Test
    public void showAllProductsByQuery() {
        Pageable paging = PageRequest.of(0, 5);
        BooleanBuilder builder = new BooleanBuilder();
        predicate = QProduct.product.numberDoors.eq(product.getNumberDoors());
        Page<ProductDto> productList = productRepository.findAll(builder.and(predicate), paging).map(ProductDto::new);

        List<ProductDto> productDtoList = productList.get().collect(Collectors.toList());

        assertThat(productDtoList).isNotEmpty();
        assertThat(productDtoList.get(0).getModel().getModel()).isEqualTo(product.getModel().getModelName());
        assertThat(productDtoList.get(0).getFirstRegistration()).isEqualTo(product.getFirstRegistration());
        assertThat(productDtoList.get(0).getNumberDoors()).isEqualTo(product.getNumberDoors());
    }
}
