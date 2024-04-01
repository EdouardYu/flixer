package web.technologies.flixer.video;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long> {
    //Optional<List<Video>> findVideoByTitle(String title);
    //Optional <Video> getVideoById(Long id);
}
