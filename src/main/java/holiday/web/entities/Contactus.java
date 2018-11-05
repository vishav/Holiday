package holiday.web.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import holiday.web.utilities.LocalDateTimeConverter;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Contactus extends ResourceSupport {

    public Contactus() {
        super();
        setDateTime(LocalDateTime.now());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @Transient
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "MESSAGE_USER"))
    private UserAccount userAccount;

    private String subject;
    private String message;
    private String reply;

    public String getDateTime() {
        return dateTime.format(formatter).toString();
    }

    private void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }


    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
