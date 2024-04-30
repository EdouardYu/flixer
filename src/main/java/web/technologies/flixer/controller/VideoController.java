package web.technologies.flixer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class VideoController {
    private MovieService movieService;

    @Autowired
    public VideoController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getMovies(){
        return movieService.getMovies();
    }

    @PostMapping()
    public ResponseEntity<String> addNewMovie(@RequestBody Movie movie) {
        movieService.addNewMovie(movie);
        return new ResponseEntity<>("Video" + movie.getTitle() + " ajouté avec succès", HttpStatus.CREATED);
    }

    @GetMapping("/tags")
    public List<Movie> getMoviesMatchingAllTags(@RequestParam List<String> tagLabel){
        return movieService.getMoviesMatchingAllTags(tagLabel);
    }

    @GetMapping("/findByName")
    public List<Movie> getMoviesContainingLetters(@RequestParam String letters){
        return movieService.getMoviesContainingLetters(letters);
    }
}
