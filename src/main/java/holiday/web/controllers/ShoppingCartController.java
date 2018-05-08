package holiday.web.controllers;

import holiday.web.entities.Item;
import holiday.web.entities.ShoppingCart;
import holiday.web.entities.UserAccount;
import holiday.web.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(method = RequestMethod.GET, value = "/getShoppingCart/{userId}")
    public List<Item> getShoppingCart(@PathVariable Long userId) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(userId);
        System.out.println("returning shopping cart");
        if(shoppingCart!=null){
            for(Item item:shoppingCart.getItem()){
                System.out.println("Country returned: "+item.getCountry());
            }
            System.out.println(shoppingCart.getItem());
            return shoppingCart.getItem();
        }

        return new ArrayList<Item>();
    }


    @RequestMapping(method = RequestMethod.POST, value = "/saveShoppingCart")
    public List<Item> saveShoppingCart(@RequestBody List<Item> items, @RequestHeader("user_id") String userId) {
        Long user_id = Long.valueOf(userId);
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(user_id);
        if(shoppingCart != null) {
            System.out.println("deleting user shopping cart");
            shoppingCartService.deleteShoppingCart(shoppingCart.getShoppingCartId());
            if(shoppingCartService.getShoppingCart(user_id)!=null){
                System.out.println("user shopping cart not deleted");
            }
        }
        if(items.isEmpty()){
            return  new ArrayList<Item>();
        }
        UserAccount temp = new UserAccount();
        temp.setUserId(user_id);

        ShoppingCart temp_ShoppingCart = new ShoppingCart();
        temp.setShoppingCart(temp_ShoppingCart);
        temp_ShoppingCart.setUserAccount(temp);

        temp_ShoppingCart.setItem(items);

        items.stream().forEach(c -> {
            c.setShoppingCart(temp_ShoppingCart);
        });
        ShoppingCart savedShoppingCart = shoppingCartService.saveShoppingCart(temp_ShoppingCart);

        return temp_ShoppingCart.getItem();
    }
}