package holiday.web.config;

import holiday.web.services.UserAccountService;
import holiday.web.utilities.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Created by karthik on 2/9/2017.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserAccountService accountService;


    @Autowired
    private CustomAuthenticationProvider authProvider;

    private String[] excludedUrls = {"/","/hselection","/register","/forgot","/changePassword.html","/resetPassword","/forgotPassword.html"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {

            http
                .authorizeRequests()
                    .antMatchers("/*").permitAll()
                    .antMatchers("/getFreeHolidays/**").permitAll()
                    .anyRequest().fullyAuthenticated()
                    .and().csrf().disable()
                    .httpBasic();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
