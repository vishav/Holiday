package holiday.web.repositories;

import holiday.web.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by karthik on 1/22/2017.
 */
public interface ItemRepository extends CrudRepository<Item,Long> {
    public List<Item> findByCheckoutOrderOrderId(Long id);
}