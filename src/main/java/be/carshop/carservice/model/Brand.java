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
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BRAND_NAME")
    private String brandName;

    @ManyToOne
    private Country country;

    @Column(name = "LOGO_URL")
    private String logoUrl;

    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Brand brandEquals = (Brand) o;
        return id != null && Objects.equals(id, brandEquals.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
