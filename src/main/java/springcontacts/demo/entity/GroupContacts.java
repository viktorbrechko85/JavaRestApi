package springcontacts.demo.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="group_contacts")
@Data
public class GroupContacts extends BaseEntity {
    @NotNull
    @Column(name="name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "groupContacts")
    private List<Contact> contacts;

    public GroupContacts(@NotNull String name) {
        this.name = name;
        this.setCreated(new Date());
        this.setUpdated(new Date());
        this.setStatus(Status.ACTIVE);
    }

    public GroupContacts() {
    }
}
