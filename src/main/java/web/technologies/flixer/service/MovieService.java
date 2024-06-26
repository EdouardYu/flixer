package web.technologies.flixer.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.technologies.flixer.dto.PurchaseDTO;
import web.technologies.flixer.dto.SearchCriteria;
import web.technologies.flixer.entity.*;
import web.technologies.flixer.repository.MovieRepository;
import web.technologies.flixer.repository.PurchaseRepository;
import web.technologies.flixer.repository.UserRepository;
import web.technologies.flixer.repository.specification.MovieSpecification;
import web.technologies.flixer.service.exception.InsufficientAmountException;
import web.technologies.flixer.service.exception.MovieNotFoundException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Transactional
@AllArgsConstructor
@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;
    private final UserService userService;

    public Page<Movie> getMovies(Pageable pageable) {
        return this.movieRepository.findAll(pageable);
    }

    public Movie getMovieById(Long id){
        return movieRepository.findMovieById(id).orElseThrow(() -> new RuntimeException("Movie with id " + id.toString() + " does not exist"));
    }

    public ResponseEntity<String> addNewMovie(Movie movie) {
        movieRepository.save(movie);
        return new ResponseEntity<>("The movie " + movie.getTitle() + "has been added", HttpStatus.CREATED);
    }

    public Page<Movie> getMoviesContainsTags(List<String> tagLabels, Pageable pageable){
        return this.movieRepository.getMoviesContainsTags(tagLabels, pageable);
    }

    public Page <Movie> getMoviesContainingLetters(String letters, Pageable pageable) {
        if(letters.isBlank())
            return this.movieRepository.findAll(pageable);

        return this.movieRepository.getMoviesContainingLetters(letters, pageable);
    }

    private boolean movieContainsAllLetters(Movie movie, String letters) {
        String movieName = movie.getTitle();
        return movieName.contains(letters);
    }

    public Page<Movie> getTopRatedMovies(Pageable pageable) {
        return movieRepository.getTopRatedMovies(pageable);
    }

    public Page<Movie> searchMovies(List<SearchCriteria> criteria, Pageable pageable) {
        MovieSpecification specs = criteria.stream()
            .map(MovieSpecification::new)
            .reduce((spec1, spec2) -> (MovieSpecification) spec1.and(spec2)).orElse(null);

        Page<Movie> movies;
        if (specs != null)
            movies = this.movieRepository.findAll(specs, pageable);
        else
            movies = this.movieRepository.findAll(pageable);

        return movies;
    }

    public void purchase(PurchaseDTO purchaseDTO) {
        User user = this.userService.hasPermission(purchaseDTO.getUserId());

        Movie movie = this.movieRepository.findMovieById(purchaseDTO.getMovieId())
            .orElseThrow(() -> new MovieNotFoundException("Movie with id " + purchaseDTO.getMovieId() + " does not exist"));

        if(user.getAmount().subtract(BigDecimal.valueOf(movie.getPrice())).compareTo(BigDecimal.ZERO) < 0)
            throw new InsufficientAmountException("Transaction failed, insufficient amount");

        Instant now = Instant.now();
        user.setAmount(user.getAmount().subtract(BigDecimal.valueOf(movie.getPrice())));
        user.setLastUpdate(now);
        user = this.userRepository.save(user);

        Purchase purchase = Purchase.builder()
            .user(user)
            .movie(movie)
            .purchasedAt(now)
            .build();

        this.purchaseRepository.save(purchase);
    }

    public boolean isPurchased(PurchaseDTO purchaseDTO) {
        this.userService.hasPermission(purchaseDTO.getUserId());

        this.movieRepository.findMovieById(purchaseDTO.getMovieId())
            .orElseThrow(() -> new MovieNotFoundException("Movie with id " + purchaseDTO.getMovieId() + " does not exist"));

        return this.purchaseRepository
            .existsByUserIdAndMovieId(purchaseDTO.getUserId(), purchaseDTO.getMovieId());
    }

    public Page<Movie> discoverMovies(Pageable pageable) {
        return this.movieRepository.findAllByOrderByReleasedAtDesc(pageable);
    }
}
