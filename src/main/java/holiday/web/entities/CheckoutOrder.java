package holiday.web.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class CheckoutOrder extends ResourceSupport {

	    public CheckoutOrder()
	    {
	     	super();
	        setDateTime(LocalDateTime.now().toString());
     	}

		@Id @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long orderId;
	  
	    private Double total;
	    
	    private String DateTime;

	    @JsonIgnore
	    @ManyToOne
	    @JoinColumn(name="user_id",foreignKey = @ForeignKey(name = "ORDER_USER"))
	    private UserAccount userAccount;

	    //@Transient
		//@JsonIgnore
	   // public int user_id ;

	    @JsonIgnore
	    @OneToMany(mappedBy="checkoutOrder", cascade=CascadeType.ALL)
	    private List<Item> item = new ArrayList<Item>();

	    public String getDateTime()
		{
			return DateTime;
		}

		private void setDateTime(String dateTime)
		{
			DateTime = dateTime;
		}

//		public int getUserId()  // bug<- u
//		{
//	    	user_id =  userAccount.getId();
//	    	return user_id;
//		}

		
		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public Double getTotal() {
			return total;
		}

		public void setTotal(Double total) {
			this.total = total;
		}


		public UserAccount getUserAccount() {
			return userAccount;
		}

		public void setUserAccount(UserAccount userAccount) {
			this.userAccount = userAccount;
		}


		public List<Item> getItem() {
			return item;
		}

		public void setItem(List<Item> item) {
			this.item = item;
		}

	   
	
}
