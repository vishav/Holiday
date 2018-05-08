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

    @RequestMapping(method = RequestMethod.POST, value = "/payment")
    public PaymentResponse payment(@RequestBody Payment payment)
    {
        PaymentResponse paymentResponse = paymentService.payment(payment);

        return paymentResponse;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/refund")
    public RefundResponse refundPayment(@RequestBody Refund refund)
    {
        return paymentService.refundPayment(refund);
    }
}
