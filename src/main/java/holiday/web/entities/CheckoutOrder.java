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
public class CheckoutOrder extends ResourceSupport {

    public CheckoutOrder() {
        super();
        setDateTime(LocalDateTime.now());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private Double total;

    @Transient
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime dateTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "ORDER_USER"))
    private UserAccount userAccount;

    @OneToMany(mappedBy = "checkoutOrder", cascade = CascadeType.ALL)
    private List<Item> item = new ArrayList<Item>();

    private String paymentId;

    private double refundAmount;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime refundDate;

    private String refundId;

    public String getDateTime() {
        return dateTime.format(formatter).toString();
    }

    private void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundDate() {
        if(refundDate==null){
            return "";
        }else {
            return Optional.ofNullable(refundDate.format(formatter).toString()).orElse("");
        }
    }

    public void setRefundDate(LocalDateTime refundDate) {
        this.refundDate = refundDate;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }
}
