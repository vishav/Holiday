package holiday.web.repositories;

import holiday.web.entities.Pricing;
import holiday.web.entities.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by karthik on 1/22/2017.
 */
@Repository
public interface PricingRepository extends CrudRepository<Pricing,Long> {

    // @Override
    // @PreAuthorize("hasRole('ROLE_ADMIN')")

//    Pricing findAll().;
//
//    Pricing save(Pricing pricing);
}