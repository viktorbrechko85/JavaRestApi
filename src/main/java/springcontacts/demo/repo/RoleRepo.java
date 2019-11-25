package springcontacts.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import springcontacts.demo.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
