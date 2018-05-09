package holiday.web.entities;

import java.math.BigDecimal;

public class Nonce
{
    private String nonce;
    private BigDecimal chargeAmount;

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public BigDecimal getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(BigDecimal chargeAmount) {
        this.chargeAmount = chargeAmount;
    }
}

