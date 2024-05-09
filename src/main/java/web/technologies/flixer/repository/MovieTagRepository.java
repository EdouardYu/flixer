package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.technologies.flixer.entity.MovieTag;
import web.technologies.flixer.entity.MovieTagId;
@Repository
public interface MovieTagRepository extends JpaRepository<MovieTag, MovieTagId> {

}
