package uz.quickly_write_html.entitiy;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private Integer lastWpm;
    private Timestamp startTime;
    @ManyToOne
    private Group group;

    public User(String fullName, String userName, String email, String password) {
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getRecordWpm() {
        return recordWpm;
    }

    public void setRecordWpm(Integer recordWpm) {
        this.recordWpm = recordWpm;
    }

    public Integer getLastWpm() {
        return lastWpm;
    }

    public void setLastWpm(Integer lastWpm) {
        this.lastWpm = lastWpm;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


}
