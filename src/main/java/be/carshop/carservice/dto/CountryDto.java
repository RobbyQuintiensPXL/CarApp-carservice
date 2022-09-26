package be.carshop.carservice.dto;

import be.carshop.carservice.model.Country;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CountryDto {

    private final Long id;
    private final String country;

    public CountryDto(Country country) {
        this.id = country.getId();
        this.country = country.getCountryName();
    }
}
