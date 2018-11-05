package holiday.web.repositories;

import holiday.web.entities.Contactus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContactusRepository extends CrudRepository<Contactus,Long> {
    public List<Contactus> findByUserAccountUserIdOrderByDateTimeDesc(Long userId);
    public List<Contactus> findAllByOrderByDateTimeDesc();
    public Contactus findOne(Long id);

}
