package web.technologies.flixer.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@Data
public class UserModificationDTO {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 32, message = "Username must be between 3 and 32 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must be alphanumeric with underscores allowed")
    private String username;

    @NotNull(message = "Birthday cannot be null")
    @Past(message = "Birthday must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date birthday;
}
