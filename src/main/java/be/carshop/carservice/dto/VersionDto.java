package be.carshop.carservice.dto;

import be.carshop.carservice.model.Emission;
import be.carshop.carservice.model.Model;
import be.carshop.carservice.model.Version;
import lombok.Getter;

@Getter
public class VersionDto {

    private final Long id;
    private final String versionName;
    private final Model model;
    private final int cylinder;
    private final int co2;
    private final Emission emission;

    public VersionDto(Version version) {
        this.id = version.getId();
        this.versionName = version.getVersionName();
        this.model = version.getModel();
        this.cylinder = version.getCylinder();
        this.co2 = version.getCo2();
        this.emission = version.getEmission();
    }
}
