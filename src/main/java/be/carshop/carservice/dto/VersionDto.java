package be.carshop.carservice.dto;

import be.carshop.carservice.model.Emission;
import be.carshop.carservice.model.FuelType;
import be.carshop.carservice.model.Model;
import be.carshop.carservice.model.Version;
import lombok.Getter;

@Getter
public class VersionDto {

    private final Long id;
    private final String versionName;
    private final ModelDto model;
    private final int cylinder;
    private final int co2;
    private final Emission emission;
    private final FuelType fuelType;
    private final int hp;
    private final String label;

    public VersionDto(Version version) {
        this.id = version.getId();
        this.versionName = version.getVersionName();
        this.model = getModelDto(version.getModel());
        this.cylinder = version.getCylinder();
        this.co2 = version.getCo2();
        this.emission = version.getEmission();
        this.fuelType = version.getFuelType();
        this.hp = version.getHp();
        this.label = version.getLabel();
    }

    private ModelDto getModelDto(Model model) {
        return new ModelDto(model);
    }
}
