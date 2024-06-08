package web.technologies.flixer.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class HistoryId implements Serializable {
    @Column(name = "movie_id", insertable = false, updatable = false)
    private Long movieId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;
}