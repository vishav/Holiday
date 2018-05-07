package holiday.web.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import holiday.web.utilities.LocalDateTimeConverter;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@XmlRootElement
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class UserAccount extends ResourceSupport{
	
	public UserAccount(){}

	public UserAccount(UserAccount user)
	{
		this.setUserId(user.getUserId());
//		this.setUserName(user.getUserName());
		this.setEmail(user.getEmail());
		this.setPassword(user.getPassword());
//		this.setContactNo(user.getContactNo());
	}
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;

	@ManyToMany(fetch = FetchType.EAGER)
    @XmlTransient
    @JsonIgnore
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

    @Transient
    private boolean isAuthenticated ;

//    private String userName;

	@Column(length = 100)
	private String password; // encrypt and use ssl
 
    private String email;
    
//    private String contactNo;

    private String fname;

    private String lname;

//    @JsonIgnore
//	@JsonBackReference
    @OneToMany(mappedBy="userAccount")
    private List<CheckoutOrder> checkoutOrder = new ArrayList<CheckoutOrder>();

	@JsonIgnore
	@OneToOne(mappedBy="userAccount")
	private ShoppingCart shoppingCart;

	@Transient
	private long expiryDate;

	private String resetUuid;

	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime resetDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public String getContactNo() {
//		return contactNo;
//	}
//
//	public void setContactNo(String contactNo) {
//		this.contactNo = contactNo;
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public List<CheckoutOrder> getCheckoutOrder() {
		return checkoutOrder;
	}

	public void setCheckoutOrder(List<CheckoutOrder> checkoutOrder) {
		this.checkoutOrder = checkoutOrder;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getResetUuid() {
		return resetUuid;
	}

	public void setResetUuid(String resetUuid) {
		this.resetUuid = resetUuid;
	}

	public LocalDateTime getResetDate() {
		return resetDate;
	}

	public void setResetDate(LocalDateTime resetDate) {
		this.resetDate = resetDate;
	}
}
