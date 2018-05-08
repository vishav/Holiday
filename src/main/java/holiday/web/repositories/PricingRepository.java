package holiday.web.repositories;

import holiday.web.entities.Pricing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingRepository extends CrudRepository<Pricing,Long> {
}