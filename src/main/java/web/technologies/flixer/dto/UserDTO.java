package web.technologies.flixer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import web.technologies.flixer.entity.Role;
import web.technologies.flixer.entity.Subscription;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private BigDecimal amount;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthday;
    private Role role;
    @JsonProperty("active_subscription")
    private Subscription activeSubscription;
}