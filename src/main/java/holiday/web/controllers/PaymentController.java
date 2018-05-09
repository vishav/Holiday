package holiday.web.controllers;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.fasterxml.jackson.databind.util.JSONPObject;
import holiday.web.entities.*;
import holiday.web.services.OrderService;
import holiday.web.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET, value = "/clientToken")
    public ResponseEntity<ClientToken> getClientToken()
    {
        String token = paymentService.getClientToken();

        ClientToken ct = new ClientToken(token);

        return new ResponseEntity<ClientToken>(ct,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/payment")
    public ResponseEntity<Result<Transaction>> payment(@RequestBody Nonce nonce)
    {
        Result<Transaction> paymentResponse = paymentService.payment(nonce);

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }

/*    @RequestMapping(method = RequestMethod.POST, value = "/payment")
    public PaymentResponse payment(@RequestBody Payment payment)
    {
        PaymentResponse paymentResponse = paymentService.payment(payment);

        return paymentResponse;
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/refund")
    public RefundResponse refundPayment(@RequestBody Refund refund)
    {
        return paymentService.refundPayment(refund);
    }
}
