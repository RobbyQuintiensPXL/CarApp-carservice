package be.carshop.carservice.controller;

import be.carshop.carservice.dto.ModelDto;
import be.carshop.carservice.service.ModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public/models")
public class ModelController {

    private final ModelService modelService;

    public ModelController(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public ResponseEntity<List<ModelDto>> getAllModels() {
        return ResponseEntity.ok(modelService.getAllModels());
    }

    @GetMapping("/{brand}")
    public ResponseEntity<List<ModelDto>> getAllModelsByBrand(@PathVariable("brand") String brand) {
        return ResponseEntity.ok(modelService.getAllModelsByBrand(brand));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ModelDto> getModelById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(modelService.getModelById(id));
    }

}
