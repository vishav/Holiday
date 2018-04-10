package holiday.web.entities;



import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class ShoppingCart extends ResourceSupport {

    public ShoppingCart()
    {
        super();
    }

    @JsonIgnore
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shoppingCartId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="user_id",foreignKey = @ForeignKey(name = "SHOPPING_CART_USER"))
    private UserAccount userAccount;

    @OneToMany(mappedBy="shoppingCart", cascade=CascadeType.ALL)
    private List<Item> item = new ArrayList<Item>();

    public Long getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Long shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
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