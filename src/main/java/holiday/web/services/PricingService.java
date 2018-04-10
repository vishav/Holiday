package holiday.web.services;

import holiday.web.entities.Holiday;
import holiday.web.entities.Pricing;
import holiday.web.repositories.OrderRepository;
import holiday.web.repositories.PricingRepository;
import holiday.web.utilities.CommHolidayTemplate;
import holiday.web.utilities.CommTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PricingService {

    @Autowired
    private PricingRepository pricingRepository;

    public Pricing getPricing() {
        if(pricingRepository==null) System.out.println("repo is null");
        Pricing price =  pricingRepository.findOne(1L);
        return price;
    }

    public Pricing savePricing(Pricing price){
        price.setPriceID(1L);
        Pricing p = pricingRepository.save(price);
        return p;
    }
}