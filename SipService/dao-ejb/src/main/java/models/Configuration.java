package models;

import lombok.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity(name = "configurations")
@NamedQueries({
        @NamedQuery(name = "configurationFindById", query = "select s from configurations s where s.name = :name"),
        @NamedQuery(name = "configurationSelectAll", query = "select s from configurations s")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Configuration implements Serializable {

    @Id
    @Column(name = "name",length = 255)
    private String name;

    @Column(name = "description",length = 255)
    private String description;
}
