package holiday.web.repositories;

import holiday.web.entities.CheckoutOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OrderRepository extends CrudRepository<CheckoutOrder,Long> {
    public List<CheckoutOrder> findByUserAccountUserIdOrderByDateTimeDesc(Long id);
}
