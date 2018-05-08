package holiday.web.entities;

public interface TransactionResponse {

    String getFname();

    String getLname();

    String getEmail();

    CheckoutOrder getCheckoutOrder();
}
