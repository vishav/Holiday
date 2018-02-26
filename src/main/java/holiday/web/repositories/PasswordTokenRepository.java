package holiday.web.repositories;

import holiday.web.entities.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by karthik on 3/31/2017.
 */
public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);
}
