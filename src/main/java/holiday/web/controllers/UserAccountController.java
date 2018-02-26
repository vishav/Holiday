package holiday.web.controllers;

import holiday.web.entities.UserAccount;
import holiday.web.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by karthik on 1/22/2017.
 */

@RestController
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    public  void self(){}

    @RequestMapping("/token")
    public Map<String,String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

    @RequestMapping("/users")
    public List<UserAccount> getAllUsers()
    {

        return userAccountService.getAllUsers();

    }

    @RequestMapping("/users/{id}")
    public UserAccount getUser(@PathVariable Long id)
    {
        return userAccountService.getUser(id);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/authenticate")
    public UserAccount AuthenticateUser(@RequestBody UserAccount userAccount)
    {
        return userAccountService.authenticateUser(userAccount);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/register")
    public UserAccount RegisterUser(@RequestBody UserAccount userAccount)
    {
        return userAccountService.saveUser(userAccount);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/forgot")
    public void forgotUser(HttpServletRequest request, @RequestBody UserAccount userAccount)
    {
         userAccountService.resetPassword(request.getContextPath(),
                 request.getLocale(),userAccount);
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/resetPasswordPage")
//    public ResponseEntity<String> getResetPage(@RequestParam("id") long id, @RequestParam("token") String token)
//    {
//        String res = userAccountService.validatePasswordResetToken(id,token);
//        if(res != null)return null;
//        Response.temporaryRedirect(new URI("path"));
//        return new ResponseEntity<String>("redirect:/changePassword.html?id="+id+"&token="+token, responseHeaders, HttpStatus.CREATED);
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/resetPassword")
    public UserAccount resetPassword(@RequestParam("id") long id, @RequestParam("token") String token, @RequestBody UserAccount userToUpdate)
    {
        return userAccountService.updatePassword(id, token,userToUpdate);
    }

    @RequestMapping(method = RequestMethod.PUT,value = "/users")
    public UserAccount updateUser(@RequestBody UserAccount userAccount)
    {
        return userAccountService.saveUser(userAccount);
    }

    @RequestMapping(method = RequestMethod.DELETE,value = "/users")
    public void deleteUser(UserAccount userAccount)
    {
        userAccountService.deleteUser(userAccount);
    }
}
