package web.technologies.flixer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.technologies.flixer.entity.*;
import web.technologies.flixer.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public List<Movie> getMovies(int limit) {
        return movieRepository.findRandomNMovies(limit);
    }

    public void addNewMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public List<Movie> getMoviesMatchingAllTags(List<String> tagLabels) {
        List<Movie> allMovies = movieRepository.findAll();
        List<Movie> matchingMovies = new ArrayList<>();

        for (Movie movie : allMovies) {
            if (isMovieContainsAllTags(movie, tagLabels)) {
                matchingMovies.add(movie);
            }
        }
        return matchingMovies;
    }

    private boolean isMovieContainsAllTags(Movie movie, List<String> tagLabels) {
        List<String> movieTagLabels = new ArrayList<>();
        for (Tag tag : movie.getTags()) {
            movieTagLabels.add(tag.getLabel());
        }
        Collections.sort(movieTagLabels);
        Collections.sort(tagLabels);
        return movieTagLabels.equals(tagLabels);
    }

    public List <Movie> getMoviesContainingLetters(String letters) {
        List<Movie> allMovies = movieRepository.findAll();
        List<Movie> matchingMovies = new ArrayList<>();

        for (Movie movie : allMovies) {
            if (movieContainsAllLetters(movie, letters)) {
                matchingMovies.add(movie);
            }
        }
        return matchingMovies;
    }

    private boolean movieContainsAllLetters(Movie movie, String letters) {
        String movieName = movie.getTitle();
        return movieName.contains(letters);
    }

    public List<Movie> getTopRatedMovies(int limit) {
        return movieRepository.getTopRatedMovies(limit);
    }
}
