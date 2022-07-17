package be.carshop.carservice.dto;

import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.FuelType;
import be.carshop.carservice.model.Model;
import lombok.Getter;

@Getter
public class ModelDto {

    private final Long id;
    private final String model;
    private final Brand brand;
    private final FuelType fuelType;

    public ModelDto(Model model) {
        this.id = model.getId();
        this.model = model.getModelName();
        this.brand = model.getBrand();
        this.fuelType = model.getFuelType();
    }
}
