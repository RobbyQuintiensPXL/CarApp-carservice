package be.carshop.carservice.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MODEL_NAME")
    private String modelName;

    @ManyToOne
    private Brand brand;

    private FuelType fuelType;

    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Model modelEquals = (Model) o;
        return id != null && Objects.equals(id, modelEquals.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
