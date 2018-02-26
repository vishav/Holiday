package holiday.web.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Item extends ResourceSupport {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)        
    private Long itemID;
    	
    private Integer optionValue;
       
    private String state;
    
    private String city;
      
    private String year;

	private String country;
  
    private Double price;

//	@JsonIgnore
//    @Transient
//    public int checkout_id ;


	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="order_id",foreignKey = @ForeignKey(name = "ITEM_ORDER"))
    public CheckoutOrder checkoutOrder;


//	public int getCheckoutId()
//	{
//		checkout_id = checkoutOrder.getId();
//		return checkout_id;
//	}


	public Long getItemID() {
		return itemID;
	}


	public void setItemID(Long itemID) {
		this.itemID = itemID;
	}


	public Integer getOptionValue() {
		return optionValue;
	}


	public void setOptionValue(Integer option) {
		this.optionValue = option;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}	


	public String getState()
	{
		return state;
	}


	public void setState(String state)
	{
		this.state = state;
	}


	public String getCity()
	{
		return city;
	}


	public void setCity(String city)
	{
		this.city = city;
	}


	public String getYear()
	{
		return year;
	}


	public void setYear(String year)
	{
		this.year = year;
	}

	public CheckoutOrder getCheckoutOrder() {
		return checkoutOrder;
	}


	public void setCheckoutOrder(CheckoutOrder checkoutOrder) {
		this.checkoutOrder = checkoutOrder;
	}
	


}
