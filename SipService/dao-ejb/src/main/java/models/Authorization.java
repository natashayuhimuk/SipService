package models;

import lombok.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity(name = "authorization")
@NamedQueries({
        @NamedQuery(name = "authorizationFindById", query = "select s from authorization s where s.id = :name"),
        @NamedQuery(name = "authorizationSelectAll", query = "select s from authorization s"),
        @NamedQuery(name = "authorizationFindByUser", query = "select s from authorization s where s.login = :user")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Authorization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "phoneNumber")
    private User login;

    @Column(name = "password")
    private String password;

    public Authorization(User login, String password) {
        this.login = login;
        this.password = password;
    }
}
