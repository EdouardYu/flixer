package web.technologies.flixer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.entity.TagLabel;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class AddMovieDTO {
    private String title;
    private String url;
    private String description;
    private Long supplierId;
    private String poster_url;
    private Float price;
    private LocalDate released_at;
    private String director;
    private List<TagLabel> labelTag;
}