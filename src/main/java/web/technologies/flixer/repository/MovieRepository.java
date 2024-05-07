package web.technologies.flixer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.technologies.flixer.entity.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT * FROM movie ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
    List<Movie> findRandomNMovies(int limit);

    @Query(value = "SELECT m.*, AVG(r.value) as avg_rating FROM movie m INNER JOIN Rating r ON m.id = r.movie_id GROUP BY m.id ORDER BY avg_rating DESC LIMIT ?1", nativeQuery = true)
    List<Movie> getTopRatedMovies(int limit);
}
