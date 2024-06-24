package booklet.physics.repository;

import booklet.physics.entity.AccountProfileEntity;
import booklet.physics.entity.DiaryPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountProfileRepository extends JpaRepository<AccountProfileEntity, Long> {

}
