package holiday.web.controllers;

import holiday.web.entities.ResetPassword;
import holiday.web.entities.UserAccount;
import holiday.web.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by karthik on 1/22/2017.
 */

@RestController
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

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

    @RequestMapping(method = RequestMethod.POST,value = "/login")
    public UserAccount AuthenticateUser(@RequestBody UserAccount userAccount)
    {
        return userAccountService.authenticateUser(userAccount);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/register")
    public UserAccount RegisterUser(@RequestBody UserAccount userAccount)
    {
        UserAccount authenticatedUserAccount = userAccountService.loadUserByUsername(userAccount.getEmail());
        if (authenticatedUserAccount != null){
            return null;
        }
        return userAccountService.saveUser(userAccount);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/changePassword")
    public ResetPassword changePassword(HttpServletRequest request, @RequestBody ResetPassword resetPassword)
    {
        userAccountService.updatePassword(resetPassword);
        return resetPassword;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/resetPassword")
    public ResetPassword resetPassword(HttpServletRequest request, @RequestBody UserAccount userToUpdate)
    {
        String url= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        ResetPassword response = userAccountService.resetPassword(userToUpdate, new StringBuilder(url));

        return response;
    }

    @RequestMapping("/uuiduser/{uuid}")
    public UserAccount getUserByUUID(@PathVariable String uuid)
    {
        UserAccount user = userAccountService.getUserByUUID(uuid);
        if(user==null){
            return null;
        }
        user.setPassword(null);
        return user;
    }
}
