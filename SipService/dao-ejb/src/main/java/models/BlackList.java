package models;

import lombok.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name = "blacklist")
@NamedQueries({
        @NamedQuery(name = "blacklistFindById", query = "select s from blacklist s where s.id = :name"),
        @NamedQuery(name = "blacklistSelectAll", query = "select s from blacklist s"),
        @NamedQuery(name = "blacklistFindByUser", query = "select s from blacklist s where s.user = :user")
})
@Getter
@Setter
@NoArgsConstructor
public class BlackList implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "phoneNumber")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "blacklist_user",
            inverseJoinColumns = @JoinColumn(name = "user_id_in_blacklist"),
            joinColumns = @JoinColumn(name = "blacklist_id")
    )
    private Set<User> users;

    public BlackList(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BlackList{" +
                "id=" + id +
                ", user=" + user +
                ", users=" + users +
                '}';
    }
}

