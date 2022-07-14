package be.carshop.carservice.dto;

import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Country;

public class BrandDto {

    private final Long id;
    private final String brand;
    private final Country country;
    private final String url;
    private final String logoUrl;

    public BrandDto(Brand brand) {
        this.id = brand.getId();
        this.brand = brand.getBrand();
        this.country = brand.getCountry();
        this.url = brand.getUrl();
        this.logoUrl = brand.getLogoUrl();
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public Country getCountry() {
        return country;
    }

    public String getUrl() {
        return url;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}
