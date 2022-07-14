package be.carshop.carservice.service;

import be.carshop.carservice.dto.ModelDto;
import be.carshop.carservice.exception.BusinessException;
import be.carshop.carservice.repository.ModelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModelService {

    private final ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public List<ModelDto> getAllModels() {
        List<ModelDto> modelDtoList = modelRepository.findAll().stream().map(ModelDto::new)
                .collect(Collectors.toList());

        if (modelDtoList.isEmpty()) {
            throw new BusinessException("No models found");
        }

        return modelDtoList;
    }

    public List<ModelDto> getAllModelsByBrand(String brand) {
        List<ModelDto> modelDtoList = modelRepository.findAllByBrand_BrandName(brand).stream().map(ModelDto::new)
                .collect(Collectors.toList());

        if (modelDtoList.isEmpty()) {
            throw new BusinessException("No models found");
        }

        return modelDtoList;
    }

    public ModelDto getModelByModelName(String model) {
        return modelRepository.findByModelName(model).map(ModelDto::new)
                .orElseThrow(() -> new BusinessException("No model found"));
    }
}
