package holiday.web.services;

import javax.mail.MessagingException;

import holiday.web.entities.*;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    public List<UserAccount> getAllUsers() {
        return StreamSupport.stream(userAccountRepository.findAll().spliterator(), false)
                .map(u -> {
                    u.setPassword(null);
                    return u;
                })
                .collect(Collectors.toList());

    }

    public UserAccount getUser(Long id) {
        UserAccount u = userAccountRepository.findOne(id);
        u.setPassword(null);
        return u;
    }

    public UserAccount authenticateUser(UserAccount user) {
        if (user == null) return null;
        UserAccount authenticatedUserAccount = null;
        authenticatedUserAccount = loadUserByUsername(user.getEmail());
        if (authenticatedUserAccount != null && matchEncodedStrings(user.getPassword(), authenticatedUserAccount.getPassword())) {
            System.out.println("user password matched");
            authenticatedUserAccount.setAuthenticated(true);
        } else {
            System.out.println("password not matched:"+user.getEmail());
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
        userAccount.setPassword(encoder.encode(userAccount.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        userAccount.setRoles(roles);

        userAccount = userAccountRepository.save(userAccount);

        userAccount.setExpiryDate(System.currentTimeMillis() + 7 * 86400000);
        userAccount.setPassword(null);
        return userAccount;
    }

    public ResetPassword resetPassword(UserAccount userAccount, StringBuilder url) {
        ResetPassword response = new ResetPassword();
        UserAccount user = userAccountRepository.findByEmail(userAccount.getEmail());
        if (user == null) {
            System.out.println("user not found:"+ user.getEmail());
            response.setMessage("Invalid Request");
            response.setSuccess(false);
            return response;
        }
        String token = null;
        try {
            token = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        url.append("/#/").append("changepassword/").append(token);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        LocalDateTime currentDate = LocalDateTime.now();

        // save the token and the rest date
        user.setResetUuid(token);
        user.setResetDate(currentDate);

        String message = "Click the link to Reset Password";

        String body = message + " \r\n" + url.toString();
        Notification notification = new Notification(user, body);
        try {
            notification.sendEmailMessage();
        } catch (MessagingException e) {
            response.setMessage("Unable to send reset email");
            response.setSuccess(false);
            e.printStackTrace();
            return response;
        }

        userAccountRepository.save(user);

        response.setMessage("Reset password email sent to user");
        response.setSuccess(true);
        return response;

    }

    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAccount authenticatedUserAccount = null;
        authenticatedUserAccount = userAccountRepository.findByEmail(email);
        if (authenticatedUserAccount == null) {
            throw new UsernameNotFoundException("Username " + email + " not found");
        }
        return new CustomUserDetails(authenticatedUserAccount);
    }

    public void updatePassword(ResetPassword resetPassword) {

        //update password
        UserAccount user = getUserByUUID(resetPassword.getRequesteduuid());
        if (user != null) {
            if(matchEncodedStrings(resetPassword.getPassword(), user.getPassword())){
                resetPassword.setMessage("New password can't be same as the current/old password");
                resetPassword.setSuccess(false);
                return;
            }
            user.setPassword(encoder.encode(resetPassword.getPassword()));
            user.setResetDate(LocalDateTime.now());
            user.setResetUuid(null);
            user = userAccountRepository.save(user);
            resetPassword.setMessage("Password changed successfully. Please login using the new password");
            resetPassword.setSuccess(true);
        }else{
            resetPassword.setMessage("Invalid request");
            resetPassword.setSuccess(false);
        }

        resetPassword.setPassword(null);
        resetPassword.setRequesteduuid(null);
    }

    public List<TransactionResponse> getAllTransactions(String email, String fname, String lname, String country, String state, String city, LocalDateTime fromDate, LocalDateTime toDate) {
        List<TransactionResponse> transactionResponse = userAccountRepository.findTransactions(email, fname, lname, country, state, city, fromDate, toDate);

        return transactionResponse;
    }

    public UserAccount getUserByUUID(String uuid) {
        UserAccount user = userAccountRepository.findByResetUuid(uuid);
        return user;
    }
}
