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


@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;


    @RequestMapping("/orders/{userId}")
    public List<CheckoutOrder> getAllOrders(@PathVariable Long userId)
    {
        return orderService.getAllOrders(userId);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/orders")
    public CheckoutOrder addOrder(@RequestBody CheckoutOrder checkoutOrderTemp, @RequestHeader("userId") String userId)
    {
        Long user_Id = Long.valueOf(userId);
        List<Item> items = checkoutOrderTemp.getItem();
        UserAccount temp = new UserAccount();
        temp.setUserId(user_Id);
        CheckoutOrder checkoutOrder = new CheckoutOrder();
        Double total = checkoutOrderTemp.getTotal();
        checkoutOrder.setTotal(total);
        temp.setCheckoutOrder(Arrays.asList((new CheckoutOrder[]{checkoutOrder})));
        checkoutOrder.setUserAccount(temp);
        checkoutOrder.setPaymentId(checkoutOrderTemp.getPaymentId());
        CheckoutOrder savedCheckoutOrder = orderService.saveOrder(checkoutOrder);
        savedCheckoutOrder.setItem(items);
        items.stream().forEach(item -> {
            item.setCheckoutOrder(savedCheckoutOrder);
            itemService.saveItem(item);
        });
        return savedCheckoutOrder;
    }
}
