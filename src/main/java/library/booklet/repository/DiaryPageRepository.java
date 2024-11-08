package library.booklet.repository;

import library.booklet.entity.DiaryPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface DiaryPageRepository extends JpaRepository<DiaryPageEntity, Long> {

    Collection<DiaryPageEntity> findByWrittenDate(@Param("writtenDate") LocalDate writtenDate);
}
