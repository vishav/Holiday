package holiday.web.repositories;

import holiday.web.entities.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by karthik on 1/22/2017.
 */
public interface UserAccountRepository extends CrudRepository<UserAccount,Long> {

   // @Override
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    Page<UserAccount> findAll(Pageable pageable);

    //@Override
   // @PostAuthorize("returnObject.firstName == principal.username or hasRole('ROLE_ADMIN')")
    UserAccount findOne(Long id);

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    List<UserAccount> findByFirstNameLike(@Param("firstName") String firstName);

//    UserAccount findByUserName(String userName);

    UserAccount findByEmail(String email);
}
