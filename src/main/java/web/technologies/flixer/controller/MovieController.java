package web.technologies.flixer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.dto.AddMovieDTO;
import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.entity.TagLabel;
import web.technologies.flixer.service.MovieService;
import web.technologies.flixer.service.MovieTagService;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {
    private final MovieService movieService;
    private final MovieTagService movieTagService;

    @Autowired
    public MovieController(MovieService movieService, MovieTagService movieTagService) {
        this.movieService = movieService;
        this.movieTagService = movieTagService;
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
    @Transactional
    public ResponseEntity<String> addNewMovieWithTag(@RequestBody AddMovieDTO addMovieDTO) {
        Movie movie = addMovieDTO.movie();
        List<TagLabel> labels = addMovieDTO.labelTag();
        try {
            movieService.addNewMovie(movie);
            movieTagService.addMovieTag(movie, labels);
            return new ResponseEntity<>("Movie added with tags", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed adding movie with tags " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tags")
        public List<Movie> getMoviesContainsTags(@RequestParam List<String> tagLabel){
            return movieService.getMoviesContainsTags(tagLabel);
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