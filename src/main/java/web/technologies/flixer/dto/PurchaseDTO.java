package web.technologies.flixer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("movie_id")
    private Long movieId;
}
