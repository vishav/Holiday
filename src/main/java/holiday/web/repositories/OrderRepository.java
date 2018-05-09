package holiday.web.repositories;

import holiday.web.entities.CheckoutOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by karthik on 1/22/2017.
 */
public interface OrderRepository extends CrudRepository<CheckoutOrder,Long> {
    public List<CheckoutOrder> findByUserAccountUserIdOrderByDateTimeDesc(Long id);
}
