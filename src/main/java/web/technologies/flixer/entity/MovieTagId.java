package web.technologies.flixer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class MovieTagId implements Serializable {
    @Column(name = "movie_id")
    private Long movieId;
    @Column(name = "tag_id")
    private Long tagId;
}
