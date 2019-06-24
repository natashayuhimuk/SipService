package models;

import lombok.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Entity(name = "statistics")
@NamedQueries({
        @NamedQuery(name = "statisticsFindById", query = "select s from statistics s where s.name = :name"),
        @NamedQuery(name = "statisticsSelectAll", query = "select s from statistics s")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Statistics implements Serializable {

    @Id
    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description")
    private AtomicLong description;

}
