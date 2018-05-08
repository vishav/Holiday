package holiday.web.services;

import holiday.web.entities.CheckoutOrder;
import holiday.web.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by karthik on 1/22/2017.
 */

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<CheckoutOrder> getAllOrders(Long userId) {
        return orderRepository.findByUserAccountUserId(userId);
    }

    public CheckoutOrder getOrder(Long id) {
        CheckoutOrder checkoutOrder = orderRepository.findOne(id);
        return checkoutOrder;
    }

    public CheckoutOrder saveOrder(CheckoutOrder checkoutOrder) {

        CheckoutOrder c = orderRepository.save(checkoutOrder);
        return checkoutOrder;
    }
}
