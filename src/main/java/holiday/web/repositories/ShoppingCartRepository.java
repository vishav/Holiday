package holiday.web.repositories;

import holiday.web.entities.ShoppingCart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart,Long> {
    public ShoppingCart findByUserAccountUserId(Long userId);

}
