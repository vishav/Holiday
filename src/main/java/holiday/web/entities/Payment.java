package holiday.web.entities;

import java.math.BigDecimal;

public class Payment {


    private String expmon;
    private String expyear;
    private String fname;
    private String lname;
    private String method;
    private String cnum;
    private BigDecimal total;
    private String type;
    private String cvv;

    public String getExpmon() {
        return expmon;
    }

    public void setExpmon(String expmon) {
        this.expmon = expmon;
    }

    public String getExpyear() {
        return expyear;
    }

    public void setExpyear(String expyear) {
        this.expyear = expyear;
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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "expMon='" + expmon + '\'' +
                ", expYear='" + expyear + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", method='" + method + '\'' +
                ", cnum='" + cnum + '\'' +
                ", total=" + total +
                ", type='" + type + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
