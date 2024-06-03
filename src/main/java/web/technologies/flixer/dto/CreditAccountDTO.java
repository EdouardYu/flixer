package web.technologies.flixer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditAccountDTO {
    @NotNull(message = "The amount must not be null")
    @Digits(integer = 10, fraction = 2, message = "The amount must have up to 2 decimal places.")
    private BigDecimal amount;

    @JsonCreator
    public CreditAccountDTO(
        Double amount
    ) {
        this.amount = new BigDecimal(amount);
    }
}
