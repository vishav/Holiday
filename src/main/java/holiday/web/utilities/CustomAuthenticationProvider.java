package holiday.web.utilities;

import holiday.web.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by karthik on 2/15/2017.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserAccountService accountService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        Collection<? extends GrantedAuthority> roles = null;
        boolean IsThirdPartySystemAuthRequired = false;
        if (IsThirdPartySystemAuthRequired) {
            // use the credentials
            // and authenticate against the third-party system
            //like google

        } else {
            // use load user by name and do a password match right here
            //so that Authenticate method can perform form login
             CustomUserDetails account = accountService.loadUserByUsername(name);
             if(account == null)throw new BadCredentialsException("Credentials do not match");
            if(accountService.matchEncodedStrings(password,account.getPassword()))
            {
                roles = account.getRoles();
            }
            else throw new BadCredentialsException("Credentials do not match");
        }
        return new UsernamePasswordAuthenticationToken(name, password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}