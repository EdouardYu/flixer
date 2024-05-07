package web.technologies.flixer.dto;

import java.math.BigDecimal;

public record SignUpDTO(String email, String username, String password, Long age, Long roleId) { }