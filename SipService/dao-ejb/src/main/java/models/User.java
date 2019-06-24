package models;


import lib.Roles;
import lombok.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "users")
@NamedQueries({
        @NamedQuery(name = "userFindById", query = "select s from users s where s.id = :name"),
        @NamedQuery(name = "userSelectAll", query = "select s from users s")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {

    @Id
    @Column(name = "phoneNumber", length = 14)
    private String phoneNumber;

    @Column(name = "firstName", length = 255)
    private String firstName;

    @Column(name = "lastName", length = 255)
    private String lastName;

    @Column(name = "secondName", length = 255)
    private String secondName;

    @OneToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "tariff_name")
    private Tariff tariff;

    @Column(name = "balance")
    private double balance;

    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;

}
