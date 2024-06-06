package web.technologies.flixer.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.technologies.flixer.entity.Movie;
import web.technologies.flixer.entity.TagLabel;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    Optional<Movie> findMovieById(Long id);

    @Query(value = "SELECT m.*, AVG(r.value) as avg_rating FROM movie m INNER JOIN Rating r ON m.id = r.movie_id GROUP BY m.id ORDER BY avg_rating DESC LIMIT ?1", nativeQuery = true)
    List<Movie> getTopRatedMovies(int limit);

    @Query(value = "SELECT DISTINCT m.* FROM movie m JOIN movie_tag mt ON m.id = mt.movie_id JOIN tag t ON mt.tag_id = t.id WHERE t.label IN :tagLabels", nativeQuery = true)
    Page<Movie> getMoviesContainsTags(List<String> tagLabels, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :term, '%'))")
    Page<Movie> getMoviesContainingLetters(String term, Pageable pageable);
}
