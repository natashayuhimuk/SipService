package models;

import lombok.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "tariffs")
@NamedQueries({
        @NamedQuery(name = "tariffFindById", query = "select s from tariffs s where s.name = :name"),
        @NamedQuery(name = "tariffSelectAll", query = "select s from tariffs s")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tariff implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private double cost;

}
