package holiday.web.services;

import holiday.web.entities.CheckoutOrder;
import holiday.web.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<CheckoutOrder> getAllOrders(Long userId) {
        return orderRepository.findByUserAccountUserIdOrderByDateTimeDesc(userId);
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
