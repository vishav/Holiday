package holiday.web.entities;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionResponse {

    String getFname();

    String getLname();

    String getEmail();

    CheckoutOrder getCheckoutOrder();
}
