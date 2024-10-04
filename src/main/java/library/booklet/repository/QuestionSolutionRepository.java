package library.booklet.repository;

import library.booklet.entity.QuestionSolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionSolutionRepository extends JpaRepository<QuestionSolutionEntity, Long> {

}
