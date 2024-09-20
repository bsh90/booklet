package library.booklet.repository;

import library.booklet.entity.LessonSolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonSolutionRepository extends JpaRepository<LessonSolutionEntity, Long> {

    public LessonSolutionEntity findByLessonPageEntityId(Long lessonPageEntityId);

}
