package web.technologies.flixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.technologies.flixer.entity.*;
import web.technologies.flixer.repository.MovieTagRepository;
import web.technologies.flixer.repository.TagRepository;

import java.util.List;

@Service
public class MovieTagService {
    private final MovieTagRepository movieTagRepository;
    private final TagRepository tagRepository;
    @Autowired
    public MovieTagService(MovieTagRepository movieTagRepository, TagRepository tagRepository){
        this.movieTagRepository = movieTagRepository;
        this.tagRepository = tagRepository;
    }

    public void addMovieTag(Movie movie, List<TagLabel> labels) {
        for (TagLabel label : labels) {
            Tag tag = tagRepository.getTagByLabel(label).orElseThrow(() -> new RuntimeException("Tag " + label + " do not exist yet"));
            MovieTag movieTag = MovieTag.builder()
                            .movie(movie)
                            .tag(tag)
                            .build();
            movieTagRepository.save(movieTag);
        }
    }
}