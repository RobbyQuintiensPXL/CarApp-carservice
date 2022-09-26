package be.carshop.carservice.repository;

import be.carshop.carservice.dto.CountryDto;
import be.carshop.carservice.model.Country;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CountryRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CountryRepository countryRepository;

    private Country country;

    @Before
    public void persist() {
        country = new Country();
        country.setCountryName("TestCountry");
        entityManager.persist(country);
        entityManager.flush();
    }

    @Test
    public void showAllCountriesTest() {
        List<CountryDto> countryDtoList = countryRepository.findAll().stream()
                .map(CountryDto::new).collect(Collectors.toList());

        assertThat(countryDtoList).isNotEmpty();
        assertThat(countryDtoList.get(0).getCountry()).isEqualTo(country.getCountryName());
    }

    @Test
    public void showCountryByIdTest() {
        CountryDto countryDto = countryRepository.findById(country.getId())
                .map(CountryDto::new).get();

        assertThat(countryDto).isNotNull();
        assertThat(countryDto.getCountry()).isEqualTo(country.getCountryName());
    }
}
