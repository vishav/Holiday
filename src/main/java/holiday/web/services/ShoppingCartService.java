package holiday.web.services;

import holiday.web.entities.*;
import holiday.web.repositories.OrderRepository;
import holiday.web.repositories.ShoppingCartRepository;
import holiday.web.utilities.CommHolidayTemplate;
import holiday.web.utilities.CommTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserAccountUserId(userId);
    }

    public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart)
    {
        ShoppingCart sc = shoppingCartRepository.save(shoppingCart);
        return sc;
    }

    public void deleteShoppingCart(Long shoppingCartID) {
        shoppingCartRepository.delete(shoppingCartID);
    }
}