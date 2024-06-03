package web.technologies.flixer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PurchaseDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("movie_id")
    private Long movieId;
}
