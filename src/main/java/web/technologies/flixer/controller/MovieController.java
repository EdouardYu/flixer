package web.technologies.flixer.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.dto.AddMovieDTO;
import web.technologies.flixer.dto.PurchaseDTO;
import web.technologies.flixer.dto.SearchCriteria;
import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.entity.TagLabel;
import web.technologies.flixer.service.MovieService;
import web.technologies.flixer.service.MovieTagService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {
    private final MovieService movieService;
    private final MovieTagService movieTagService;

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
        Movie movie = addMovieDTO.getMovie();
        List<TagLabel> labels = addMovieDTO.getLabelTag();
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

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Movie> searchMovies(@RequestBody List<SearchCriteria> criteria, Pageable pageable) {
        return this.movieService.searchMovies(criteria, pageable);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/purchase")
    public void purchase(@RequestBody PurchaseDTO purchaseDTO) {
        this.movieService.purchase(purchaseDTO);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/purchased")
    public boolean isPurchased(@RequestBody PurchaseDTO purchaseDTO) {
        return this.movieService.isPurchased(purchaseDTO);
    }
}