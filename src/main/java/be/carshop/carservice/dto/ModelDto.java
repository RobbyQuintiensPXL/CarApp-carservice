package be.carshop.carservice.dto;

import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.Model;
import lombok.Getter;

@Getter
public class ModelDto {

    private final Long id;
    private final String model;
    private final String brand;

    public ModelDto(Model model) {
        this.id = model.getId();
        this.model = model.getModelName();
        this.brand = model.getBrand().getBrandName();
    }
}
