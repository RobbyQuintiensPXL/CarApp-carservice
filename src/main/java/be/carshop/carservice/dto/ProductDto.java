package be.carshop.carservice.dto;

import be.carshop.carservice.model.*;
import lombok.Getter;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
public class ProductDto {

    private final Long id;
    private final BrandDto brand;
    private final ModelDto model;
    private final VersionDto version;
    private final int numberDoors;
    private final Transmission transmission;
    private final double price;
    private final LocalDate firstRegistration;
    private final int numberKm;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.brand = getBrandDto(product.getBrand());
        this.model = getModelDto(product.getModel());
        this.version = getVersionDto(product.getVersion());
        this.numberDoors = product.getNumberDoors();
        this.transmission = product.getTransmission();
        this.price = product.getPrice();
        this.firstRegistration = product.getFirstRegistration();
        this.numberKm = product.getNumberKm();
    }

    private BrandDto getBrandDto(Brand brand) {
        return new BrandDto(brand);
    }

    private ModelDto getModelDto(Model model) {
        return new ModelDto(model);
    }

    private VersionDto getVersionDto(Version version) {
        return new VersionDto(version);
    }
}
