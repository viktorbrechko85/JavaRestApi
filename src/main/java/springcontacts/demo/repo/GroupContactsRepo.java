package springcontacts.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import springcontacts.demo.entity.GroupContacts;
import springcontacts.demo.entity.User;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "groups", path = "groups")
public interface GroupContactsRepo extends JpaRepository<GroupContacts, Long> {
    List<GroupContacts> findByUser_Username(String name);
    List<GroupContacts> findByUser(User user);
    List<GroupContacts> findAll();

    GroupContacts findById(long id);
}
