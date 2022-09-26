package be.carshop.carservice.controller;

import be.carshop.carservice.dto.CountryDto;
import be.carshop.carservice.model.Country;
import be.carshop.carservice.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class CountryControllerTest {

    @InjectMocks
    CountryController countryController;

    @Mock
    CountryService countryService;

    private Country country;
    private List<Country> countryList;

    @Before
    public void init() {
        country = new Country();
        country.setId(1L);
        country.setCountryName("TestCountry");
        countryList = new LinkedList<>();
        countryList.add(country);
    }

    @Test
    public void getAllCountriesTest() {
        when(countryService.getAllCountries()).thenReturn(countryList.stream().map(CountryDto::new).collect(Collectors.toList()));

        ResponseEntity<List<CountryDto>> responseEntity = countryController.getAllCountries();

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getCountryByIdTest() {
        CountryDto countryDto = Optional.of(country).stream().map(CountryDto::new).findAny().get();
        when(countryService.getCountryById(anyLong())).thenReturn(countryDto);

        ResponseEntity<CountryDto> responseEntity = countryController.getCountryById(country.getId());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getCountry()).isEqualTo(country.getCountryName());
    }
}
