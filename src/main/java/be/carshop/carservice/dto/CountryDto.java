package be.carshop.carservice.dto;

import be.carshop.carservice.model.Country;

public class CountryDto {

    private final String country;

    public CountryDto(Country country) {
        this.country = country.getCountry();
    }

    public String getCountry() {
        return country;
    }
}
