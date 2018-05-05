package holiday.web.services;

import holiday.web.entities.PasswordResetToken;
import holiday.web.entities.Role;
import holiday.web.entities.TransactionResponse;
import holiday.web.entities.UserAccount;
import holiday.web.repositories.PasswordTokenRepository;
import holiday.web.repositories.RoleRepository;
import holiday.web.repositories.UserAccountRepository;
import holiday.web.utilities.CustomUserDetails;
import holiday.web.utilities.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * Created by karthik on 1/22/2017.
 */

@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserAccount> getAllUsers() {
        return StreamSupport.stream(userAccountRepository.findAll().spliterator(), false)
                .map(u -> {
                    u.setPassword(null);
//                    u.add(linkTo(methodOn(UserAccountController.class).getUser(u.getUserId())).withSelfRel());
                    return u;
                })
                .collect(Collectors.toList());

    }

    public UserAccount getUser(Long id) {
        UserAccount u = userAccountRepository.findOne(id);
        u.setPassword(null);
//        u.add(linkTo(methodOn(UserAccountController.class).getUser(u.getUserId())).withSelfRel());
//        u.add(linkTo(methodOn(UserAccountController.class).AuthenticateUser(u)).withRel("auth"));
//        u.add(linkTo(methodOn(UserAccountController.class).updateUser(u)).withRel("update"));
        return u;
    }

    public UserAccount authenticateUser(UserAccount user) {
        if (user == null) return null;
        UserAccount authenticatedUserAccount = null;
        authenticatedUserAccount = loadUserByUsername(user.getEmail());
        if (authenticatedUserAccount != null && matchEncodedStrings(user.getPassword(), authenticatedUserAccount.getPassword())) {
            System.out.println("user password matched");
            authenticatedUserAccount.setAuthenticated(true);
//            Long id = authenticatedUserAccount.getUserId();
/*                authenticatedUserAccount.add(linkTo(methodOn(UserAccountController.class).getUser(id)).withSelfRel());
                authenticatedUserAccount.add(linkTo(methodOn(OrderController.class).getAllOrders(id)).withRel("checkouts"));*/
        } else {
            return null;
        }
        authenticatedUserAccount.setPassword(null);
        authenticatedUserAccount.setExpiryDate(System.currentTimeMillis() + 7 * 86400000);

        return authenticatedUserAccount;
    }

    public boolean matchEncodedStrings(String rawPassword, String hashPassword) {
        if (encoder.matches(rawPassword, hashPassword)) return true;
        return false;
    }

    public UserAccount saveUser(UserAccount userAccount) {
        System.out.println("saving user");
        if (userAccount.getEmail() == null || userAccount.getPassword() == null) return null;

        // if email already registered, throw error
 /*       if(userAccountRepository.findByEmail(userAccount.getEmail())!=null){

        }*/
        userAccount.setPassword(encoder.encode(userAccount.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        userAccount.setRoles(roles);

        userAccount = userAccountRepository.save(userAccount);

        userAccount.setExpiryDate(System.currentTimeMillis() + 7 * 86400000);
        userAccount.setPassword(null);
        return userAccount;
    }


    public void deleteUser(UserAccount userAccount) {
        userAccountRepository.delete(userAccount.getUserId());
    }

    public void resetPassword(String contextPath, Locale locale, UserAccount userAccount) {
        UserAccount u = userAccountRepository.findByEmail(userAccount.getEmail());
        if (u == null) {
            return;
        }
        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(u, token);

        String url="";
//        String url = linkTo(methodOn(UserAccountController.class).resetPassword(u.getUserId(), token, u)).toString().replace("resetPassword", "changePassword.html");
        String message = "Click the link to Reset Password";

        String body = message + " \r\n" + url;
        Notification notification = new Notification(u, body);
        try {
            notification.sendEmailMessage();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void createPasswordResetTokenForUser(UserAccount user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount authenticatedUserAccount = null;
        authenticatedUserAccount = userAccountRepository.findByEmail(email);
        if (authenticatedUserAccount == null) {
            throw new UsernameNotFoundException("Username " + email + " not found");
        }
        return new CustomUserDetails(authenticatedUserAccount);
    }

    public UserAccount updatePassword(long id, String token, UserAccount userToUpdate) {

        String result = validatePasswordResetToken(id, token);
        if (result != null) {
            return null;
        }
        //update password
        UserAccount u = null;
        u = userAccountRepository.findByEmail(userToUpdate.getEmail());
        if (u != null) {
            if (u.getUserId() != id) return null;
            if (userToUpdate.getPassword() == null) return null;
            u.setPassword(encoder.encode(userToUpdate.getPassword()));
        }

        u = userAccountRepository.save(u);
//        u.add(linkTo(methodOn(UserAccountController.class).getUser(u.getUserId())).withSelfRel());
//        u.add(linkTo(methodOn(UserAccountController.class).AuthenticateUser(u)).withRel("auth"));
//        u.add(linkTo(methodOn(UserAccountController.class).updateUser(u)).withRel("update"));
        u.setPassword(null);
        return u;

    }

    public String validatePasswordResetToken(long id, String token) {
        PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
        if ((passToken == null) || (passToken.getUser().getUserId() != id)) {
            return "invalidToken";
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            passwordTokenRepository.delete(passToken.getId());
            return "expired";
        }

        passwordTokenRepository.delete(passToken.getId());

        UserAccount user = passToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

    public List<TransactionResponse> getAllTransactions(String email, String fname, String lname, String country, String state, String city, LocalDateTime fromDate, LocalDateTime toDate) {
        List<TransactionResponse> transactionResponse = userAccountRepository.findTransactions(email, fname, lname, country, state, city, fromDate, toDate);

        return transactionResponse;
    }
}
