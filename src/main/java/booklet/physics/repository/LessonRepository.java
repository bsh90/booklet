package booklet.physics.repository;

import booklet.physics.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

}
