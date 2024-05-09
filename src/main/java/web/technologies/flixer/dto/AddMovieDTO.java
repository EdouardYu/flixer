package web.technologies.flixer.dto;

import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.entity.TagLabel;

import java.util.List;

public record AddMovieDTO(Movie movie, List<TagLabel> labelTag) {}