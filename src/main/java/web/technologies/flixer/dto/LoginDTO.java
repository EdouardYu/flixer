package web.technologies.flixer.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginDTO {
    private String token;
    private Long id;
    private String username;
    private String email;

}
