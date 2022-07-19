package be.carshop.carservice.service;

import be.carshop.carservice.dto.ProductDto;
import be.carshop.carservice.dto.VersionDto;
import be.carshop.carservice.model.*;
import be.carshop.carservice.repository.ProductRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product product;
    private Version version;
    private Country country;
    private Brand brand;
    private Model model;
    private List<Product> productList;

    Predicate predicate;

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
        version = new Version();
        version.setId(1L);
        version.setModel(model);
        version.setEmission(Emission.EURO_6);
        version.setCylinder(2000);
        version.setCo2(100);
        version.setVersionName("VersionName");
        version.setFuelType(FuelType.DIESEL);
        product = new Product();
        product.setBrand(brand);
        product.setModel(model);
        product.setVersion(version);
        product.setPrice(10000);
        product.setFirstRegistration(LocalDate.now());
        product.setNumberDoors(5);
        product.setNumberKm(5000);
        product.setTransmission(Transmission.MANUEEL);
        productList = new LinkedList<>();
        productList.add(product);
    }

    @Test
    public void showAllProductsTest() {
        Page<Product> products= new PageImpl<>(productList);
        Pageable paging = PageRequest.of(1, 5);
//        BooleanBuilder builder = new BooleanBuilder();
//        predicate = QProduct.product.numberDoors.eq(product.getNumberDoors());
        when(productRepository.findAll(any(Pageable.class))).thenReturn(products);

        Page<ProductDto> productDtoList = productService.getAllProducts(paging.getPageSize(), paging.getPageNumber());
        List<ProductDto> productDtos = productDtoList.get().collect(Collectors.toList());

        assertEquals(productDtos.get(0).getModel().getModel(), product.getModel().getModelName());
        assertEquals(productDtos.get(0).getPrice(), product.getPrice());
    }

    @Test
    public void showAllProductsByQueryTest() {
        Page<Product> products= new PageImpl<>(productList);
        Pageable paging = PageRequest.of(1, 5);
        BooleanBuilder builder = new BooleanBuilder();
        predicate = QProduct.product.numberDoors.eq(product.getNumberDoors());
        when(productRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(products);

        Page<ProductDto> productDtoList = productService.getAllProductsByQuery(builder.and(predicate), paging.getPageSize(), paging.getPageNumber());
        List<ProductDto> productDtos = productDtoList.get().collect(Collectors.toList());

        assertEquals(productDtos.get(0).getModel().getModel(), product.getModel().getModelName());
        assertEquals(productDtos.get(0).getPrice(), product.getPrice());
    }
}
