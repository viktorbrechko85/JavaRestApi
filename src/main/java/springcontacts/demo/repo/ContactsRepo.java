package springcontacts.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import springcontacts.demo.entity.Contact;
import springcontacts.demo.entity.GroupContacts;
import springcontacts.demo.entity.User;

import java.util.List;
@RepositoryRestResource(collectionResourceRel = "contacts", path = "contacts")
public interface ContactsRepo extends JpaRepository<Contact, Long> {
    List<Contact> findByUserLike(String name);
    List<Contact> findByUser(User user);
    List<Contact> findAll();

    List<Contact> findByFirstNameLike(String name);

    List<Contact> findByLastNameLike(String name);

}
