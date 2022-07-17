package be.carshop.carservice.service;

import be.carshop.carservice.dto.VersionDto;
import be.carshop.carservice.exception.BusinessException;
import be.carshop.carservice.repository.VersionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VersionService {

    private final VersionRepository versionRepository;

    public VersionService(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    public List<VersionDto> getAllVersions() {
        List<VersionDto> versionDtoList = versionRepository.findAll().stream().map(VersionDto::new)
                .collect(Collectors.toList());

        if (versionDtoList.isEmpty()) {
            throw new BusinessException("No versions found");
        }

        return versionDtoList;
    }

    public List<VersionDto> getAllVersionsByModel(String model) {
        List<VersionDto> versionDtoList = versionRepository.findAllByModel_ModelName(model).stream()
                .map(VersionDto::new)
                .collect(Collectors.toList());

        if (versionDtoList.isEmpty()) {
            throw new BusinessException("No versions found");
        }

        return versionDtoList;
    }

    public VersionDto getVersionByName(String version) {
        return versionRepository.findByVersionName(version).map(VersionDto::new)
                .orElseThrow(() -> new BusinessException("No version found"));
    }
}
