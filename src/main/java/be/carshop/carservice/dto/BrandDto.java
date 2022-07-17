package be.carshop.carservice.dto;

import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;
import lombok.Getter;

@Getter
public class BrandDto {

    private final Long id;
    private final String brand;
    private final Country country;
    private final String url;
    private final String logoUrl;

    public BrandDto(Brand brand) {
        this.id = brand.getId();
        this.brand = brand.getBrandName();
        this.country = brand.getCountry();
        this.url = brand.getUrl();
        this.logoUrl = brand.getLogoUrl();
    }
}
