package holiday.web.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Pricing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long priceID;

    private Double countryPrice;

    private Double cityPrice;

    private Double statePrice;

    private Double minPrice;

    public Long getPriceID() {
        return priceID;
    }

    public void setPriceID(Long priceID) {
        this.priceID = priceID;
    }

    public Double getCountryPrice() {
        return countryPrice;
    }

    public void setCountryPrice(Double countryPrice) {
        this.countryPrice = countryPrice;
    }

    public Double getCityPrice() {
        return cityPrice;
    }

    public void setCityPrice(Double cityPrice) {
        this.cityPrice = cityPrice;
    }

    public Double getStatePrice() {
        return statePrice;
    }

    public void setStatePrice(Double statePrice) {
        this.statePrice = statePrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }
}