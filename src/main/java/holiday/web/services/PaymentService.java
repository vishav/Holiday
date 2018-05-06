package holiday.web.services;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import holiday.web.HolidayApplication;
import holiday.web.entities.Payment;
import holiday.web.entities.PaymentResponse;
import holiday.web.entities.Refund;
import holiday.web.entities.RefundResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private BraintreeGateway gateway = HolidayApplication.gateway;

    public PaymentResponse payment(Payment payment) {

        PaymentResponse paymentResponse = new PaymentResponse();
        String name = payment.getFname() + " " + payment.getLname();

        // Create the transaction
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount(payment.getTotal())
                .creditCard()
                .cardholderName(name)
                .cvv(payment.getCvv())
                .number(payment.getCnum())
                .expirationMonth(payment.getExpmon())
                .expirationYear(payment.getExpyear())
                .done()
                .paymentMethodNonce("fake-valid-nonce")
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(transactionRequest);

        if (result.isSuccess()) {
            Transaction transaction = result.getTarget();
            paymentResponse.setTransactionID(transaction.getOrderId());
            paymentResponse.setSuccess(true);
        } else {
            paymentResponse.setSuccess(false);
        }

        return paymentResponse;
    }

    public RefundResponse refundPayment(double refundAmount, String paymentId) {

        RefundResponse refundResponse = new RefundResponse();

        Result<Transaction> result = gateway.transaction().refund(paymentId, new BigDecimal(refundAmount));

        if (result.isSuccess()) {
            refundResponse.setSuccess(true);
        } else {
            refundResponse.setSuccess(false);
        }

        return refundResponse;
    }

}
