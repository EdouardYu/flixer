package web.technologies.flixer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.entity.TagLabel;

import java.util.List;

@Data
@AllArgsConstructor
public class AddMovieDTO {
    private Movie movie;
    private List<TagLabel> labelTag;
}