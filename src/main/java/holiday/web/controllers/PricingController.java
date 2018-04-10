package holiday.web.controllers;

import holiday.web.entities.Holiday;
import holiday.web.entities.Pricing;
import holiday.web.entities.UserAccount;
import holiday.web.services.HolidayService;
import holiday.web.services.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PricingController {

    @Autowired
    private PricingService pricingService;

    @RequestMapping(method = RequestMethod.GET, value = "/getPricing/")
    public Pricing getPricing()
    {
        return pricingService.getPricing();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/savePricing/")
    public Pricing savePricing(@RequestBody Pricing price)
    {
        return pricingService.savePricing(price);
    }
}