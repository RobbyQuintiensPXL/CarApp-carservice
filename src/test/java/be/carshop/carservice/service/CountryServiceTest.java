package be.carshop.carservice.service;

import be.carshop.carservice.dto.CountryDto;
import be.carshop.carservice.model.Country;
import be.carshop.carservice.repository.CountryRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CountryServiceTest {

    @MockBean
    private CountryRepository countryRepository;

    @Autowired
    private CountryService countryService;

    private Country country;
    private List<Country> countryList;

    @Before
    public void init() {
        country = new Country();
        country.setCountryName("TestCountry");
        countryList = new LinkedList<>();
        countryList.add(country);
    }

    @Test
    public void showAllCountriesTest() {
        when(countryRepository.findAll()).thenReturn(countryList);

        List<CountryDto> countryDtoList = countryService.getAllCountries();

        assertEquals(1, countryDtoList.size());
        assertEquals(countryDtoList.get(0).getCountry(), country.getCountryName());
    }

    @Test
    public void showCountryById() {
        when(countryRepository.findById(country.getId())).thenReturn(Optional.ofNullable(country));

        CountryDto countryDto = countryService.getCountryById(country.getId());

        assertEquals(countryDto.getCountry(), country.getCountryName());
        assertEquals(countryDto.getId(), country.getId());
    }

    @Test
    public void showCountryByCountryName() {
        when(countryRepository.findByCountryName(country.getCountryName()))
                .thenReturn(Optional.ofNullable(country));

        CountryDto countryDto = countryService.getCountryByCountryName(country.getCountryName());

        assertEquals(countryDto.getCountry(), country.getCountryName());
        assertEquals(countryDto.getId(), country.getId());
    }
}
