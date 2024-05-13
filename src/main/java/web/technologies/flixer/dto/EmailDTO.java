package web.technologies.flixer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class EmailDTO {
    private String email;

    @JsonCreator
    public EmailDTO(String email) {
        this.email = email == null ? null : email.toLowerCase();
    }
}
