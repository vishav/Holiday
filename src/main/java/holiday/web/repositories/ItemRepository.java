package holiday.web.repositories;

import holiday.web.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ItemRepository extends CrudRepository<Item,Long> {
    public List<Item> findByCheckoutOrderOrderId(Long id);
}