package library.booklet.repository;

import library.booklet.entity.AccountProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountProfileRepository extends JpaRepository<AccountProfileEntity, Long> {

}
