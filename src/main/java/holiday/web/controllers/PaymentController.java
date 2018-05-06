package holiday.web.controllers;

import holiday.web.entities.*;
import holiday.web.services.OrderService;
import holiday.web.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST, value = "/payment")
    public PaymentResponse payment(@RequestBody Payment payment)
    {
        System.out.println("userpayment:"+payment);
        PaymentResponse paymentResponse = paymentService.payment(payment);

        return paymentResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/refund")
    public RefundResponse refundPayment(@RequestBody Refund refund)
    {
        RefundResponse refundResponse = new RefundResponse();

        System.out.println("refund:"+refund);
        CheckoutOrder checkoutOrder = orderService.getOrder(refund.getOrderId());

        double originalRefundedAmount = checkoutOrder.getRefundAmount();

        // if the order_id doesn't match with any existing order, it's an invalid order
        if(checkoutOrder==null){
            refundResponse.setSuccess(false);
            refundResponse.setRefundMessage("Invalid request");
            return refundResponse;
        }

        /* if the sum of amount to be refunded and the total
         amount refunded till today > total order price, it's invalid*/
        if(refund.getRefundAmount() <= 0 || (refund.getRefundAmount() + originalRefundedAmount) > checkoutOrder.getTotal()){
            refundResponse.setSuccess(false);
            refundResponse.setRefundMessage("Invalid refund amount value");
            return refundResponse;
        }

        // get the payment id associated wih that order
        String paymentId = checkoutOrder.getPaymentId();

        // refund the amount which is asked
        refundResponse = paymentService.refundPayment(refund.getRefundAmount(), paymentId);

        // update the the total refunded amount till now
        double totalRefundedAmount = originalRefundedAmount + refund.getRefundAmount();
        checkoutOrder.setRefundAmount(totalRefundedAmount);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        LocalDateTime currentDate = LocalDateTime.now();
        // update the date when the last refund occurred
        checkoutOrder.setRefundDate(currentDate);

        // save the order
        orderService.saveOrder(checkoutOrder);

        refundResponse.setRefundAmount(totalRefundedAmount);
        refundResponse.setRefundDate(currentDate.format(formatter).toString());
        return refundResponse;
    }
}
