package web.technologies.flixer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.technologies.flixer.entity.Tag;
import web.technologies.flixer.entity.TagLabel;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> getTagByLabel(TagLabel label);
}
