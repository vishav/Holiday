package holiday.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class HolidayApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolidayApplication.class, args);
	}
}
