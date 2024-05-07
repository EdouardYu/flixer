package web.technologies.flixer.entity;

import jakarta.persistence.Column;

import java.io.Serializable;

public class HistoryId implements Serializable {
    @Column(name = "movie_id", insertable = false, updatable = false)
    private Long movieId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;
}