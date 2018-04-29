package holiday.web.controllers;

import holiday.web.entities.Payment;
import holiday.web.services.OrderService;
import holiday.web.services.PaymentService;
import holiday.web.utilities.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.POST, value = "/payment")
    public PaymentResponse payment(@RequestBody Payment payment)
    {
        System.out.println("userpayment:"+payment);
        PaymentResponse paymentResponse = paymentService.payment(payment);

        return paymentResponse;
    }
}
