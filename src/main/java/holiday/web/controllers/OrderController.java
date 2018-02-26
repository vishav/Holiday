package holiday.web.controllers;

import holiday.web.entities.CheckoutOrder;
import holiday.web.entities.Item;
import holiday.web.entities.UserAccount;
import holiday.web.services.ItemService;
import holiday.web.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by karthik on 1/22/2017.
 */

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;


    @RequestMapping("/users/{userId}/orders")
    public List<CheckoutOrder> getAllOrders(@PathVariable Long userId)
    {
        return orderService.getAllOrders(userId);
    }

    @RequestMapping("/orders/{id}")
    public CheckoutOrder getOrder(@PathVariable Long id)
    {
        return orderService.getOrder(id);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/orders")
    public CheckoutOrder addOrder(@RequestBody List<Item> checkouts, @RequestHeader("user_id") Long user_id)
    {
        UserAccount temp = new UserAccount();
        temp.setUserId(user_id);
        CheckoutOrder checkoutOrder = new CheckoutOrder();
        Double total = checkouts.stream().map(c -> c.getPrice()).reduce(0.0 , (x,y) -> x+y);
        checkoutOrder.setTotal(total);
        temp.setCheckoutOrder(Arrays.asList((new CheckoutOrder[]{checkoutOrder})));
        checkoutOrder.setUserAccount(temp);
        CheckoutOrder savedCheckoutOrder = orderService.saveOrder(checkoutOrder);
        savedCheckoutOrder.setItem(checkouts);
        checkouts.stream().forEach(c -> {
            c.setCheckoutOrder(savedCheckoutOrder);
            itemService.saveItem(c);
        });
        return savedCheckoutOrder;
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/orders")
    public CheckoutOrder updateOrder(@RequestBody CheckoutOrder checkoutOrder)
    {
        return orderService.saveOrder(checkoutOrder);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/orders")
    public void deleteOrder(CheckoutOrder checkoutOrder)
    {
        orderService.deleteOrder(checkoutOrder);
    }
}
