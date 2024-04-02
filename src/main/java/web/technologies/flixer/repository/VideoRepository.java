package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.technologies.flixer.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
    //Optional<List<Video>> findVideoByTitle(String title);
    //Optional <Video> getVideoById(Long id);
}
