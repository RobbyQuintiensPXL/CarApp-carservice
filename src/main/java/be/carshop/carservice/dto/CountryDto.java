package be.carshop.carservice.dto;

import be.carshop.carservice.model.Country;

public class CountryDto {

    private final Long id;
    private final String country;

    public CountryDto(Country country) {
        this.id = country.getId();
        this.country = country.getCountryName();
    }

    public Long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }
}
