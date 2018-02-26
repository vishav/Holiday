package holiday.web.utilities;

import holiday.web.entities.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by karthik on 2/11/2017.
 */
public class CustomUserDetails extends UserAccount implements UserDetails {
    UserAccount userAccount;

    public CustomUserDetails(UserAccount user) {
        super(user);
        userAccount = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return userAccount.getRoles();
//       return roles.stream()
//               .map(r -> new SimpleGrantedAuthority(r.getName()))
//               .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
