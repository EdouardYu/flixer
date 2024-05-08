package web.technologies.flixer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping()
    public List<Movie> getMovies(@RequestParam(required = false, defaultValue = "0") int limit){
        if (limit > 0) {
            return movieService.getMovies(limit);
        } else {
            return movieService.getMovies();
        }
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }


    @PostMapping()
    public ResponseEntity<String> addNewMovie(@RequestBody Movie movie) {
        return movieService.addNewMovie(movie);
    }

    @GetMapping("/tags")
        public List<Movie> getMoviesMatchingAllTags(@RequestParam List<String> tagLabel){
        return movieService.getMoviesMatchingAllTags(tagLabel);
    }

    @GetMapping("/findByName")
    public List<Movie> getMoviesContainingLetters(@RequestParam String letters){
        return movieService.getMoviesContainingLetters(letters);
    }

    @GetMapping("/getTopRatedMovies")
    public List<Movie> getTopRatedMovies(@RequestParam(required = false, defaultValue = "5") int limit) {
        return movieService.getTopRatedMovies(limit);
    }
}
