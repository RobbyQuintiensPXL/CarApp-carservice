package be.carshop.carservice.service;

import be.carshop.carservice.dto.BrandDto;
import be.carshop.carservice.exception.BusinessException;
import be.carshop.carservice.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<BrandDto> getAllBrands() {
        List<BrandDto> brandDtoList = brandRepository.findAll().stream().map(BrandDto::new)
                .collect(Collectors.toList());

        if (brandDtoList.isEmpty()) {
            throw new BusinessException("No brands found");
        }

        return brandDtoList;
    }

    public List<BrandDto> getAllBrandsByCountry(String country) {
        List<BrandDto> brandDtoList = brandRepository.findAllByCountry_CountryName(country).stream().map(BrandDto::new)
                .collect(Collectors.toList());

        if (brandDtoList.isEmpty()) {
            throw new BusinessException("No brands found");
        }

        return brandDtoList;
    }

    public BrandDto getBrandByBrandName(String brand) {
        return brandRepository.findByBrandName(brand).map(BrandDto::new)
                .orElseThrow(() -> new BusinessException("No brand found"));
    }
}
