package booklet.physics.repository;

import booklet.physics.entity.LessonEntity;
import booklet.physics.entity.LessonSolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonSolutionRepository extends JpaRepository<LessonSolutionEntity, Long> {

    public LessonSolutionEntity findByLessonPageEntityId(Long lessonPageEntityId);

}
