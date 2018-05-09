package holiday.web.services;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import holiday.web.HolidayApplication;
import holiday.web.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    @Autowired
    private OrderService orderService;

    private BraintreeGateway gateway = HolidayApplication.gateway;

    public String getClientToken(){
        String token = gateway.clientToken().generate();
        return token;
    }

    public Result<Transaction> payment(Nonce nonce) {

        // Create the transaction
        TransactionRequest transactionRequest = new TransactionRequest()
                .amount(nonce.getChargeAmount())
                .paymentMethodNonce(nonce.getNonce())
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(transactionRequest);

        return result;
    }

    public RefundResponse refundPayment(Refund refund) {

        RefundResponse refundResponse = new RefundResponse();

        CheckoutOrder checkoutOrder = orderService.getOrder(refund.getOrderId());

        // if the order_id doesn't match with any existing order, it's an invalid order
        if(checkoutOrder==null){
            refundResponse.setSuccess(false);
            refundResponse.setRefundMessage("Invalid request");
            return refundResponse;
        }

        double originalRefundedAmount = checkoutOrder.getRefundAmount();

        /* if the sum of amount to be refunded and the total
         amount refunded till today > total order price, it's invalid*/
        if(refund.getRefundAmount() <= 0 || (refund.getRefundAmount() + originalRefundedAmount) > checkoutOrder.getTotal()){
            refundResponse.setSuccess(false);
            refundResponse.setRefundMessage("Invalid refund amount value");
            return refundResponse;
        }

        // get the payment id associated wih that order
        String paymentId = checkoutOrder.getPaymentId();

        double refundAmount = refund.getRefundAmount();

        Transaction transaction  = gateway.transaction().find(paymentId);


        Result<Transaction> result = gateway.transaction().refund(paymentId, new BigDecimal(refundAmount));

        if (result.isSuccess()) {

            // update the the total refunded amount till now
            double totalRefundedAmount = originalRefundedAmount + refund.getRefundAmount();
            checkoutOrder.setRefundAmount(totalRefundedAmount);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
            LocalDateTime currentDate = LocalDateTime.now();
            // update the date when the last refund occurred
            checkoutOrder.setRefundDate(currentDate);
            checkoutOrder.setRefundId(result.getTarget().getId());
            // save the order
            orderService.saveOrder(checkoutOrder);

            refundResponse.setRefundAmount(totalRefundedAmount);
            refundResponse.setRefundDate(currentDate.format(formatter).toString());

            refundResponse.setSuccess(true);
            refundResponse.setRefundMessage("Refund initiated successfully");
        } else {
            refundResponse.setSuccess(false);
            refundResponse.setRefundMessage("Error occured while refunding. Please try again in some time.");
        }

        return refundResponse;
    }

}
