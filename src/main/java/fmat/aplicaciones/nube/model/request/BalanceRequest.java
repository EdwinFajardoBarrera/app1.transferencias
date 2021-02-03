package fmat.aplicaciones.nube.model.request;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BalanceRequest {

    @NotNull(message = "Favor de ingresar su balance")
    @Min(50)
    @Max(10000)
    private BigDecimal balance;

    public BalanceRequest(BigDecimal balance){
        this.balance = balance;
    }

    public BalanceRequest(){}

    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    public BigDecimal getBalance(){
        return this.balance;
    }
    
}
