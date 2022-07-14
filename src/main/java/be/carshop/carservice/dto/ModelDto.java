package be.carshop.carservice.dto;

import be.carshop.carservice.model.Brand;
import be.carshop.carservice.model.FuelType;
import be.carshop.carservice.model.Model;

public class ModelDto {

    private final Long id;
    private final String model;
    private final Brand brand;
    private final FuelType fuelType;
    private final String imageUrl;

    public ModelDto(Model model) {
        this.id = model.getId();
        this.model = model.getModel();
        this.brand = model.getBrand();
        this.fuelType = model.getFuelType();
        this.imageUrl = model.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Brand getBrand() {
        return brand;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
