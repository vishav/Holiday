package holiday.web.controllers;

import holiday.web.entities.Contactus;
import holiday.web.entities.UserAccount;
import holiday.web.services.ContactusService;
import holiday.web.repositories.ContactusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
public class ContactusController {

    @Autowired
    private ContactusService contactusService;

   @Autowired
    private ContactusRepository contactusRepository;

    @RequestMapping("/messages")
    public List<Contactus> getAllMessages(@RequestHeader("userId") String userId)
    {
        List<Contactus> all_messages =  contactusService.getAllMessages(Long.valueOf(userId));
        System.out.printf("total messages:%d%n",all_messages.size());
        return all_messages;
    }

    @RequestMapping(method = RequestMethod.POST,value = "/sendMessage")
    public Contactus addMessage(@RequestBody Contactus contactusTemp, @RequestHeader("userId") String userId)
    {

        Contactus tempContactUs = null;
        if(contactusTemp.getMessageId()!=-1){
            tempContactUs = contactusRepository.findOne(contactusTemp.getMessageId());
            if(contactusTemp.getReply() != null && !contactusTemp.getReply().isEmpty() && !contactusTemp.equals("null")){
                tempContactUs.setReply(contactusTemp.getReply());
            }
        }else{
            UserAccount temp = new UserAccount();
            temp.setUserId(Long.valueOf(userId));
            
            tempContactUs = new Contactus();
            tempContactUs.setSubject(contactusTemp.getSubject());
            tempContactUs.setMessage(contactusTemp.getMessage());
            tempContactUs.setUserAccount(temp);
        }

        Contactus savedContactUs = contactusService.saveMessage(tempContactUs);
        savedContactUs.setUserAccount(null);
        return savedContactUs;
    }
}
