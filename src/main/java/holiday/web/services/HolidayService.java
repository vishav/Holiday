package holiday.web.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HolidayService {

    final String baseURL = "http://www.worldholidaysandevents.com/HolidaysRESTJSON/webresources/holidaysandevents";
    final String countriesUrl = baseURL + "/countries";
    final String statesUrl = baseURL + "/countryStates/";
    final String countryCitiesUrl = baseURL + "/countryCities/";
    final String citiesUrl = baseURL + "/countryStateCities/";

    public String getFreeHolidays(String country, String state, String city, int fromYear, int fromMonth, int fromDay, int toYear, int toMonth, int toDay) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(baseURL, String.class);

        System.out.println(result);

        return result;
    }

    public String getAllCountries() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(countriesUrl, String.class);

        System.out.println(result);

        return result;
    }

    public String getStates(String country) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(statesUrl + country, String.class);

        System.out.println(result);

        return result;
    }

    public String getCountryCities(String country) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(countryCitiesUrl + country, String.class);

        System.out.println(result);

        return result;
    }

    public String getCities(String country, String state) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(citiesUrl + country + "/" + state, String.class);

        System.out.println(result);

        return result;
    }
}
