package holiday.web.utilities;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import holiday.web.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;


/**
 * Created by karthik on 2/5/2017.
 */


public class Notification {

    String holidayServer = "mea2nmailer@gmail.com";
    String holidayPass = "starfishwhite";

    String subject;
    String body;
    UserAccount userAccount;

    @Autowired
    private Environment env;

    public Notification(UserAccount userAccount, String message)  {
        body = message;
        subject = "Request For Password Reset Holidays App";
        this.userAccount = userAccount;
    }

    private class SMTPAuthenticator extends Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(holidayServer, holidayPass);
        }
    }

    public void sendEmailMessage() throws MessagingException {

        System.out.println("username:"+holidayServer);

        // Get system properties
        Properties props ;//= System.getProperties();
        props = new Properties();
        props.put("mail.smtp.userAccount", holidayServer);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");


        SMTPAuthenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(props, auth);
        session.setDebug(false);

        MimeMessage msg = new MimeMessage(session);
        msg.setText(body);
        msg.setSubject("Request For Password Reset Holidays App");
        msg.setFrom(new InternetAddress(holidayServer));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(userAccount.getEmail()));

        Transport transport = session.getTransport("smtps");
        transport.connect("smtp.gmail.com", 465, holidayServer, holidayPass);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();

    }

}
