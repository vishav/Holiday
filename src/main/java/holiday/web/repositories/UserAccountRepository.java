package holiday.web.repositories;

import holiday.web.entities.TransactionResponse;
import holiday.web.entities.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    Page<UserAccount> findAll(Pageable pageable);

    UserAccount findOne(Long id);

    UserAccount findByResetUuid(String id);

    UserAccount findByEmail(String email);

    @Query("Select distinct ua.fname as fname, ua.lname as lname, " +
            "ua.email as email, co as checkoutOrder from  UserAccount ua " +
            "INNER JOIN ua.checkoutOrder co " +
            "INNER JOIN co.item it " +
            "where (:email is null or ua.email=:email) "+
            "and (:fname is null or ua.fname= :fname) " +
            "and (:lname is null or ua.lname= :lname) "+
            "and (:fromDate is null or co.dateTime >= :fromDate) "+
            "and (:toDate is null or co.dateTime <= :toDate) "+
            "and (:country is null or it.country=:country) "+
            "and (:state is null or it.state=:state) "+
            "and (:city is null or it.city=:city) " +
            "order by co.dateTime desc")
    List<TransactionResponse> findTransactions(@Param("email") String email, @Param("fname") String fname, @Param("lname") String lname, @Param("country") String country, @Param("state") String state, @Param("city") String city, @Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);
}