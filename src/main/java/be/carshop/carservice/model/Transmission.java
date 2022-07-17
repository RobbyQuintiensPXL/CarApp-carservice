package be.carshop.carservice.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Transmission {

    AUTOMAAT("automaat"), DUAL_CLUTCH("dual clutch"), MANUEEL("manueel"),
    HALFAUTOMAAT("halfautomaat");

    private static final Map<String, Transmission> nameToValueMap =
            new HashMap<>();

    static {
        for (Transmission value : EnumSet.allOf(Transmission.class)) {
            nameToValueMap.put(value.name(), value);
        }
    }

    private final String transmission;

    private Transmission(String transmission) {
        this.transmission = transmission;
    }

    public static Transmission forName(String name) {
        return nameToValueMap.get(name);
    }

    public String getTransmission() {
        return transmission;
    }
}
