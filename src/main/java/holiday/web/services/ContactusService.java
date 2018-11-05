package holiday.web.services;

import holiday.web.entities.Contactus;
import holiday.web.repositories.ContactusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import holiday.web.services.UserAccountService;

import java.util.List;


@Service
public class ContactusService {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private ContactusRepository contactusRepository;

    public List<Contactus> getAllMessages(Long userId) {
        boolean is_admin = userAccountService.getUser(userId).getRoles().stream().filter(role->role.getName().equals("ROLE_ADMIN")).findFirst().isPresent();
        if(is_admin){
            return contactusRepository.findAllByOrderByDateTimeDesc();
        }else{
            return contactusRepository.findByUserAccountUserIdOrderByDateTimeDesc(userId);
        }
    }


    public Contactus saveMessage(Contactus contactusTemp) {
        Contactus c = contactusRepository.save(contactusTemp);
        return c;
    }
}
