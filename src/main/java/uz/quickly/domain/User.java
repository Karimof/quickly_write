package uz.quickly.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    @Column(nullable = false)
    private String userName;

    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean active;

    private Integer recordWpm;

    private Timestamp startTime;

    private String photoName;

    @ManyToOne
    private Group groups;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(String fullName, String userName, String email, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
