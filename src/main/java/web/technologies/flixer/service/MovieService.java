package web.technologies.flixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.entity.Tag;
import web.technologies.flixer.entity.TagLabel;
import web.technologies.flixer.repository.MovieRepository;
import web.technologies.flixer.entity.MovieTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Movie> getMovies(){
        return movieRepository.findAll();
    }

    public void addNewMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public List<Movie> getMoviesMatchingAllTags(List<String> tagLabels) {
        List<Movie> allMovies = movieRepository.findAll();
        List<Movie> matchingMovies = new ArrayList<>();

        for (Movie movie : allMovies) {
            if (movieContainsAllTags(movie, tagLabels)) {
                matchingMovies.add(movie);
            }
        }
        return matchingMovies;
    }

    private boolean movieContainsAllTags(Movie movie, List<String> tagLabels) {
        List<String> movieTagLabels = new ArrayList<>();
        for (Tag tag : movie.getTags()) {
            movieTagLabels.add(tag.getLabel());
        }
        Collections.sort(movieTagLabels);
        Collections.sort(tagLabels);
        return movieTagLabels.equals(tagLabels);
    }
}
