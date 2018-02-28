package holiday.web.services;

import holiday.web.entities.Holiday;
import holiday.web.utilities.CommHolidayTemplate;
import holiday.web.utilities.CommTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class HolidayService {

    final String baseURL = "http://www.worldholidaysandevents.com/HolidaysRESTJSON/webresources/holidaysandevents";
    final String holidaysUrl = baseURL + "/holidaysAndEvents/";
    final String countriesUrl = baseURL + "/countries";
    final String statesUrl = baseURL + "/countryStates/";
    final String countryCitiesUrl = baseURL + "/countryCities/";
    final String citiesUrl = baseURL + "/countryStateCities/";

    public List<Holiday> getFreeHolidays(String country, String state, String city, int fromYear, int fromMonth, int fromDay, int toYear, int toMonth, int toDay) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(holidaysUrl);
        String url = holidaysUrl + country + '/' + state + '/' + city + '/' + fromYear + '/' + fromMonth + '/' + fromDay + '/' + toYear + '/' + toMonth + '/' + toDay;
        ResponseEntity<CommHolidayTemplate> holidayResponse =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<CommHolidayTemplate>() {
                        });
        CommHolidayTemplate holidays = holidayResponse.getBody();
        return holidays.getTheList();
    }

    public String[] getAllCountries() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CommTemplate> response =
                restTemplate.exchange(countriesUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<CommTemplate>() {
                        });
        CommTemplate countries = response.getBody();
        return countries.getTheList();

    }

    public String[] getStates(String country) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CommTemplate> response =
                restTemplate.exchange(statesUrl + country,
                        HttpMethod.GET, null, new ParameterizedTypeReference<CommTemplate>() {
                        });
        CommTemplate states = response.getBody();
        return states.getTheList();
    }

    public String[] getCountryCities(String country) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CommTemplate> response =
                restTemplate.exchange(countryCitiesUrl + country,
                        HttpMethod.GET, null, new ParameterizedTypeReference<CommTemplate>() {
                        });
        CommTemplate states = response.getBody();
        return states.getTheList();
    }

    public String[] getCities(String country, String state) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CommTemplate> response =
                restTemplate.exchange(citiesUrl + country + "/" + state,
                        HttpMethod.GET, null, new ParameterizedTypeReference<CommTemplate>() {
                        });
        CommTemplate states = response.getBody();
        return states.getTheList();
    }
}
