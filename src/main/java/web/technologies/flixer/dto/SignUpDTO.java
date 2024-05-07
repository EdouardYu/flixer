package web.technologies.flixer.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SignUpDTO(String email, String username, String password, LocalDate birthday, Long roleId) { }