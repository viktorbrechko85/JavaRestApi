package springcontacts.demo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@Data
public class User extends BaseEntity {

    @Column(name="first_name", nullable = true)
    private String firstName;

    @Column(name="last_name", nullable = true)
    private String lastName;

    @NotNull
    @Column(name="username", nullable = false)
    private String username;

    @NotNull
    @Column(name="password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
                joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName = "id")}
            )
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<GroupContacts> groupContacts;

    @OneToMany(mappedBy = "user")
    private List<Contact> contacts;

    public User(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
        this.setCreated(new Date());
        this.setUpdated(new Date());
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
