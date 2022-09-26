package be.carshop.carservice.createresource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateProduct {

    private final String brand;
    private final String model;
    private final String version;
    private final int numberDoors;
    private final String transmission;
    private final double price;
    private final LocalDate firstRegistration;
    private final int numberKm;
    private final String performance;
    private final List<String> images;
}
