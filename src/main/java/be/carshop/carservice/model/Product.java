package be.carshop.carservice.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Model model;

    @ManyToOne
    private Version version;

    private int numberDoors;

    private Transmission transmission;

    private double price;

    private LocalDate firstRegistration;

    private int numberKm;

    //uitrusting
    //carrosserie
    //aantal deuren
    //kleur

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product productEquals = (Product) o;
        return id != null && Objects.equals(id, productEquals.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
