package holiday.web.controllers;

import holiday.web.entities.ResetPassword;
import holiday.web.entities.UserAccount;
import holiday.web.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @RequestMapping(method = RequestMethod.GET, value = "/resetPasswordPage")
//    public ResponseEntity<String> getResetPage(@RequestParam("id") long id, @RequestParam("token") String token)
//    {
//        String res = userAccountService.validatePasswordResetToken(id,token);
//        if(res != null)return null;
//        Response.temporaryRedirect(new URI("path"));
//        return new ResponseEntity<String>("redirect:/changePassword.html?id="+id+"&token="+token, responseHeaders, HttpStatus.CREATED);
//    }

    @RequestMapping(method = RequestMethod.POST, value = "/resetPassword")
    public ResetPassword resetPassword(HttpServletRequest request, @RequestBody UserAccount userToUpdate)
    {
/*        if(userAccountService.verifyUserHasRequiredRole(userToUpdate.getUserId(), "ROLE_ADMIN") == null){
            ResetPasswordResponse response = new ResetPasswordResponse();
            response.setMessage("Invalid Request");
            response.setSuccess(false);
            return response;
        }*/
        String url= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        ResetPassword response = userAccountService.resetPassword(userToUpdate, new StringBuilder(url));

        return response;
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
