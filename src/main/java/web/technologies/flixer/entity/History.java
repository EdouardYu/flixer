package web.technologies.flixer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history", schema = "public")
public class History {
    @EmbeddedId
    private HistoryId id;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Instant watched_at;

    public History(Long movieId, Long userId, Instant watched_at) {
        this.id = new HistoryId(movieId, userId);
        this.watched_at = watched_at;
    }
}

