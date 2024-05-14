package web.technologies.flixer.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import web.technologies.flixer.entity.*;
import web.technologies.flixer.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public List<Movie> getMovies(){
        return movieRepository.findAll();
    }

    public List<Movie> getMovies(int limit) {
        return movieRepository.findRandomNMovies(limit);
    }

    public Movie getMovieById(Long id){
        return movieRepository.findMovieById(id).orElseThrow(() -> new RuntimeException("Movie with id " + id.toString() + " does not exist"));
    }

    public ResponseEntity<String> addNewMovie(Movie movie) {
        movieRepository.save(movie);
        return new ResponseEntity<>("The movie " + movie.getTitle() + "has been added", HttpStatus.CREATED);
    }

    public List<Movie> getMoviesContainsTags(List<String> tagLabels){
        return movieRepository.getMoviesContainsTags(tagLabels);
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
