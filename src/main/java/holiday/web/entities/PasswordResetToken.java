package holiday.web.entities;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by karthik on 3/31/2017.
 */
@Entity
public class PasswordResetToken {

    public PasswordResetToken()
    {

    }

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = UserAccount.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserAccount user;

    private Date expiryDate;

    public PasswordResetToken(String token, UserAccount user) {
        this.token = token;
        this.user = user;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE ,1);
        this.expiryDate = c.getTime();
    }

    public static int getEXPIRATION() {
        return EXPIRATION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}