package holiday.web.controllers;

import holiday.web.entities.TransactionResponse;
import holiday.web.services.UserAccountService;
import holiday.web.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private UserAccountService userAccountService;

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/{country}/{state}/{city}/{fname}/{lname}/{useremail}/{fromDate}/{toDate}")
    public List<TransactionResponse> getTransactions(@PathVariable String country, @PathVariable String state, @PathVariable String city, @PathVariable String fname, @PathVariable String lname, @PathVariable String useremail, @PathVariable String fromDate, @PathVariable String toDate) {

        List<TransactionResponse> transactionResponse = getAllTransactions(country, state, city, fname, lname, useremail, fromDate, toDate);

        return transactionResponse;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/download/{country}/{state}/{city}/{fname}/{lname}/{useremail}/{fromDate}/{toDate}")
    public void downloadTransactions(HttpServletResponse response, @PathVariable String country, @PathVariable String state, @PathVariable String city, @PathVariable String fname, @PathVariable String lname, @PathVariable String useremail, @PathVariable String fromDate, @PathVariable String toDate) {

        List<TransactionResponse> transactionResponse = getAllTransactions(country, state, city, fname, lname, useremail, fromDate, toDate);
        Utility.downloadXLSX(transactionResponse, response);
    }


    public List<TransactionResponse> getAllTransactions(String country, String state, String city, String fname, String lname, String useremail, String fromDate, String toDate) {
        country = convertToNullIfEmpty(country, "Country");
        state = convertToNullIfEmpty(state, "State");
        city = convertToNullIfEmpty(city, "City");
        fname = convertToNullIfEmpty(fname, "000000");
        lname = convertToNullIfEmpty(lname, "000000");
        useremail = convertToNullIfEmpty(useremail, "000000");
        fromDate = convertToNullIfEmpty(fromDate, "000000");
        toDate = convertToNullIfEmpty(toDate, "000000");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime fromLocalDate = null;
        LocalDateTime toLocalDate = null;

        if (fromDate != null) {
            fromLocalDate = LocalDate.parse(fromDate, formatter).atStartOfDay();
            System.out.println("fromLocalDate:"+fromLocalDate);
        }

        if (toDate != null) {
            toLocalDate = LocalDate.parse(toDate, formatter).atStartOfDay();
            System.out.println("toLocalDate:"+toLocalDate);
        }

        List<TransactionResponse> transactionResponse = userAccountService.getAllTransactions(useremail, fname, lname, country, state, city, fromLocalDate, toLocalDate);

        return transactionResponse;
    }


    private String convertToNullIfEmpty(String parameter, String textToClean) {

        parameter = parameter.equals(textToClean) ? null : parameter;
        return parameter;
    }
}