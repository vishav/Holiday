package holiday.web;

import com.braintreegateway.BraintreeGateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.io.File;

@SpringBootApplication
public class HolidayApplication {

	public static String DEFAULT_CONFIG_FILENAME = "config.properties";
	public static BraintreeGateway gateway;

	public static void main(String[] args) {

        File configFile = new File(DEFAULT_CONFIG_FILENAME);
        try {
            if(configFile.exists() && !configFile.isDirectory()) {
                gateway = BraintreeGatewayFactory.fromConfigFile(configFile);
            } else {
                gateway = BraintreeGatewayFactory.fromConfigMapping(System.getenv());
            }
        } catch (NullPointerException e) {
            System.err.println("Could not load Braintree configuration from config file or system environment.");
            System.exit(1);
        }

	    SpringApplication.run(HolidayApplication.class, args);
	}
}
