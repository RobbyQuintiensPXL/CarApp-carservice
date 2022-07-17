package be.carshop.carservice.controller;

import be.carshop.carservice.dto.VersionDto;
import be.carshop.carservice.service.VersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/versions")
public class VersionController {

    private final VersionService versionService;

    public VersionController(VersionService versionService) {
        this.versionService = versionService;
    }

    @GetMapping
    public ResponseEntity<List<VersionDto>> getAllVersions() {
        return ResponseEntity.ok(versionService.getAllVersions());
    }

    @GetMapping("/{model}")
    public ResponseEntity<List<VersionDto>> getAllVersionsByModel(@PathVariable("model") String model) {
        return ResponseEntity.ok(versionService.getAllVersionsByModel(model));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<VersionDto> getVersionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(versionService.getVersionById(id));
    }

}
