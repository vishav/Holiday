package holiday.web.repositories;

import holiday.web.entities.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByName(String role_user);
}
