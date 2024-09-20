package library.booklet.repository;

import library.booklet.entity.DiaryPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryPageRepository extends JpaRepository<DiaryPageEntity, Long> {

}
