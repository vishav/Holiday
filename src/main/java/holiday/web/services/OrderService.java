package holiday.web.services;

import holiday.web.controllers.ItemController;
import holiday.web.controllers.OrderController;
import holiday.web.entities.CheckoutOrder;
import holiday.web.entities.Item;
import holiday.web.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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


    public void deleteOrder(CheckoutOrder checkoutOrder) {
        orderRepository.delete(checkoutOrder.getOrderId());
    }
}
