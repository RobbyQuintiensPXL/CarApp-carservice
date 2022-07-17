package be.carshop.carservice.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Emission {

    EURO_4("EURO 4"), EURO_5("EURO 5"), EURO_6("EURO 6");

    private static final Map<String, Emission> nameToValueMap =
            new HashMap<>();

    static {
        for (Emission value : EnumSet.allOf(Emission.class)) {
            nameToValueMap.put(value.name(), value);
        }
    }

    private final String emission;

    private Emission(String emission) {
        this.emission = emission;
    }

    public static Emission forName(String name) {
        return nameToValueMap.get(name);
    }

    public String getEmission() {
        return emission;
    }

}
