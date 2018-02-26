package holiday.web.repositories;

import holiday.web.entities.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by karthik on 2/12/2017.
 */
public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByName(String role_user);
}
