package be.carshop.carservice.service;

import be.carshop.carservice.dto.CountryDto;
import be.carshop.carservice.exception.BusinessException;
import be.carshop.carservice.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    public final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository){
        this.countryRepository = countryRepository;
    }

    public List<CountryDto> getAllCountries() {
        List<CountryDto> countryDtoList = countryRepository.findAll().stream()
                .map(CountryDto::new).collect(Collectors.toList());

        if(countryDtoList.isEmpty()){
            throw new BusinessException("No countries found");
        }

        return countryDtoList;
    }

    public CountryDto getCountryById(Long id) {
        return countryRepository.findById(id).map(CountryDto::new).orElseThrow(() -> new BusinessException("No country found"));
    }
}
