package be.carshop.carservice.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FuelType {

    PETROL("petrol"), DIESEL("diesel"), HYBRIDE("hybride"),
    ELECTRIC("Electric");

    private static final Map<String, FuelType> nameToValueMap =
            new HashMap<>();

    static {
        for (FuelType value : EnumSet.allOf(FuelType.class)) {
            nameToValueMap.put(value.name(), value);
        }
    }

    private final String fuel;

    private FuelType(String fuel) {
        this.fuel = fuel;
    }

    public static FuelType forName(String name) {
        return nameToValueMap.get(name);
    }

    public String getFuel() {
        return fuel;
    }

}
