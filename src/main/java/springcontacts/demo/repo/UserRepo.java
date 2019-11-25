package springcontacts.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import springcontacts.demo.entity.GroupContacts;
import springcontacts.demo.entity.User;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepo extends PagingAndSortingRepository<User, Long> {
    User findByUsername(String name);


    @Query("select count(u) from User u")
    Long findCount();
}
