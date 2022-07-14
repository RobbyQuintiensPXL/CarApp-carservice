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
        country.setCountry("TestCountry");
        countryList = new LinkedList<>();
        countryList.add(country);
    }

    @Test
    public void showAllCountriesTest() {
        when(countryRepository.findAll()).thenReturn(countryList);

        List<CountryDto> countryDtoList = countryService.getAllCountries();

        assertThat(countryDtoList.size()).isNotNull();
        assertEquals(countryDtoList.get(0).getCountry(), country.getCountry());
    }

    @Test
    public void showCountryById() {
        when(countryRepository.findById(country.getId())).thenReturn(Optional.ofNullable(country));

        CountryDto countryDto = countryService.getCountryById(country.getId());

        assertThat(countryDto).isNotNull();
        assertEquals(countryDto.getCountry(), country.getCountry());
        assertEquals(countryDto.getId(), country.getId());
    }

    @Test
    public void showCountryByCountryName() {
        when(countryRepository.findByCountry(country.getCountry())).thenReturn(Optional.ofNullable(country));

        CountryDto countryDto = countryService.getCountryByCountryName(country.getCountry());

        assertThat(countryDto).isNotNull();
        assertEquals(countryDto.getCountry(), country.getCountry());
        assertEquals(countryDto.getId(), country.getId());
    }
}
