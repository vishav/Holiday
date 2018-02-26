package holiday.web.controllers;

import holiday.web.entities.UserAccount;
import holiday.web.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class holidayController {

    @Autowired
    private HolidayService holidayService;

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/{country}/{state}/{city}/{fromYear}/{fromMonth}/{fromDay}/{toYear}/{toMonth}/{toDay}")
    public String getFreeHolidays(@PathVariable String country, @PathVariable String state, @PathVariable String city, @PathVariable int fromYear, @PathVariable int fromMonth, @PathVariable int fromDay, @PathVariable int toYear, @PathVariable int toMonth, @PathVariable int toDay)
    {
        return holidayService.getFreeHolidays(country, state, city, fromYear, fromMonth, fromDay, toYear, toMonth, toDay);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/countries")
    public String getAllCountries()
    {
        return holidayService.getAllCountries();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/countryStates/{country}")
    public String getStates(@PathVariable String country)
    {
        return holidayService.getStates(country);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/countryCities/{country}")
    public String getCountryCities(@PathVariable String country)
    {
        return holidayService.getCountryCities(country);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFreeHolidays/countryStateCities/{state}/{country}")
    public String getCities(@PathVariable String country, @PathVariable String state)
    {
        return holidayService.getCities(country, state);
    }
}
