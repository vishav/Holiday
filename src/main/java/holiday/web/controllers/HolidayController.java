package holiday.web.controllers;

import holiday.web.entities.CheckoutOrder;
import holiday.web.entities.Holiday;
import holiday.web.entities.Item;
import holiday.web.entities.UserAccount;
import holiday.web.services.HolidayService;
import holiday.web.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/{country}/{state}/{city}/{fromYear}/{fromMonth}/{fromDay}/{toYear}/{toMonth}/{toDay}")
    public List<Holiday> getFreeHolidays(@PathVariable String country, @PathVariable String state, @PathVariable String city, @PathVariable int fromYear, @PathVariable int fromMonth, @PathVariable int fromDay, @PathVariable int toYear, @PathVariable int toMonth, @PathVariable int toDay)
    {
        return holidayService.getFreeHolidays(country, state, city, fromYear, fromMonth, fromDay, toYear, toMonth, toDay);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/countries")
    public String[] getAllCountries()
    {
        return holidayService.getAllCountries();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/countryStates/{country}")
    public String[] getStates(@PathVariable String country)
    {
        return holidayService.getStates(country);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/countryCities/{country}")
    public String[] getCountryCities(@PathVariable String country)
    {
        return holidayService.getCountryCities(country);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/countryStateCities/{state}/{country}")
    public String[] getCities(@PathVariable String country, @PathVariable String state)
    {
        return holidayService.getCities(country, state);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/holidays/{country}/{state}/{city}/{fromYear}/{fromMonth}/{fromDay}/{toYear}/{toMonth}/{toDay}")
    public List<Holiday> getPaidHolidays(@RequestHeader("userId") String userId, @RequestHeader("orderId") String orderId, @PathVariable String country, @PathVariable String state, @PathVariable String city, @PathVariable int fromYear, @PathVariable int fromMonth, @PathVariable int fromDay, @PathVariable int toYear, @PathVariable int toMonth, @PathVariable int toDay)
    {
        Long order_id = Long.valueOf(orderId);
        Long user_id = Long.valueOf(userId);
        CheckoutOrder checkoutOrder = orderService.getOrder(order_id);
        if(checkoutOrder!=null && user_id == checkoutOrder.getUserAccount().getUserId()){
            List<Item> items = checkoutOrder.getItem();
            for(Item item: items){
                if(item.getCountry().equalsIgnoreCase(country) && item.getState().equalsIgnoreCase(state) && item.getCity().equalsIgnoreCase(city) && item.getFromYear() == fromYear && item.getFromMonth() == fromMonth && item.getFromDay() == fromDay && item.getToYear() == toYear && item.getToMonth() == toMonth && item.getToDay() == toDay){
                    return holidayService.getFreeHolidays(country, state, city, fromYear, fromMonth, fromDay, toYear, toMonth, toDay);
                }
            }
        }else{
            return null;
        }
        return null;
    }

/*    @RequestMapping("/hselection")
    public String getHselection()
    {
        return "hselection";
    }

    @RequestMapping("/instructions")
    public void getInstructions()
    {
//        return "hselection";
    }*/
}
