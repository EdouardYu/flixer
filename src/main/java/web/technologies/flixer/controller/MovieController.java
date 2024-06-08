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
import web.technologies.flixer.entity.User;
import web.technologies.flixer.service.MovieService;
import web.technologies.flixer.service.MovieTagService;
import web.technologies.flixer.service.UserService;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:5173")
public class MovieController {
    private final MovieService movieService;
    private final MovieTagService movieTagService;
    private final UserService userService;

    @GetMapping()
    public Page<Movie> getMovies(Pageable pageable){
        return this.movieService.getMovies(pageable);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<String> addNewMovieWithTag(@RequestBody AddMovieDTO addMovieDTO) {
        String title = addMovieDTO.getTitle();
        String url = addMovieDTO.getUrl();
        String description = addMovieDTO.getDescription();
        Long supplierId = addMovieDTO.getSupplierId();
        String posterUrl = addMovieDTO.getPoster_url();
        Float price = addMovieDTO.getPrice();
        LocalDate releasedAt = addMovieDTO.getReleased_at();
        String director = addMovieDTO.getDirector();
        User supplier = this.userService.getUserEntityById(supplierId);
        Movie movie = Movie.builder()
                .title(title)
                .url(url)
                .description(description)
                .supplier(supplier)
                .poster_url(posterUrl)
                .price(price)
                .released_at(releasedAt)
                .director(director)
                .build();
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
        public Page<Movie> getMoviesContainsTags(@RequestParam List<String> tagLabel, Pageable pageable){
            return this.movieService.getMoviesContainsTags(tagLabel, pageable);
    }

    @GetMapping("/findByName")
    public Page<Movie> getMoviesContainingLetters(@RequestParam String letters, Pageable pageable){
        return this.movieService.getMoviesContainingLetters(letters, pageable);
    }

    @GetMapping("/getTopRatedMovies")
    public Page<Movie> getTopRatedMovies(Pageable pageable) {
        return movieService.getTopRatedMovies(pageable);
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

    @GetMapping(path = "/discover")
    public Page<Movie> discoverMovies(Pageable pageable) {
        return this.movieService.discoverMovies(pageable);
    }
}