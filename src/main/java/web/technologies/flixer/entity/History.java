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
@IdClass(HistoryId.class)
public class History {
    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Instant watched_at;

}

