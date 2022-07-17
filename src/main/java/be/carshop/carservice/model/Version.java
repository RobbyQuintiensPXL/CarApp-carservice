package be.carshop.carservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Model model;

    @Column(name = "VERSION_NAME")
    private String versionName;

    private int cylinder;

    private int co2;

    private Emission emission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Version versionEquals = (Version) o;
        return id != null && Objects.equals(id, versionEquals.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
