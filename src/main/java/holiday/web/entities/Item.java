package holiday.web.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Item extends ResourceSupport {
	
	@Id
	@JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)        
    private Long itemID;

    private String state;
    
    private String city;

    @NotNull
    private int fromYear;

	private int fromMonth;

	private int fromDay;

	private int toYear;

	private int toMonth;

	private int toDay;

	@NotNull
	private String country;
  
    private Double price;

//	@JsonIgnore
//    @Transient
//    public int checkout_id ;


	@JsonIgnore
    @ManyToOne
    @JoinColumn(name="order_id",foreignKey = @ForeignKey(name = "ITEM_ORDER"))
    public CheckoutOrder checkoutOrder;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="shopping_cart_id",foreignKey = @ForeignKey(name = "ITEM_SHOPPING_CART"))
	public ShoppingCart shoppingCart;


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

	public int getFromYear() {
		return fromYear;
	}

	public void setFromYear(int fromYear) {
		this.fromYear = fromYear;
	}

	public int getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(int fromMonth) {
		this.fromMonth = fromMonth;
	}

	public int getFromDay() {
		return fromDay;
	}

	public void setFromDay(int fromDay) {
		this.fromDay = fromDay;
	}

	public int getToYear() {
		return toYear;
	}

	public void setToYear(int toYear) {
		this.toYear = toYear;
	}

	public int getToMonth() {
		return toMonth;
	}

	public void setToMonth(int toMonth) {
		this.toMonth = toMonth;
	}

	public int getToDay() {
		return toDay;
	}

	public void setToDay(int toDay) {
		this.toDay = toDay;
	}

	public CheckoutOrder getCheckoutOrder() {
		return checkoutOrder;
	}


	public void setCheckoutOrder(CheckoutOrder checkoutOrder) {
		this.checkoutOrder = checkoutOrder;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}
}
