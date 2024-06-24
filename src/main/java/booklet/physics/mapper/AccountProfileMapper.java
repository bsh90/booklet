package booklet.physics.mapper;

import booklet.physics.dto.AccountProfileDTO;
import booklet.physics.entity.AccountProfileEntity;

@Mapper
public interface AccountProfileMapper {
    AccountProfileEntity to(AccountProfileDTO diaryPageDTO);

    AccountProfileDTO from(AccountProfileEntity diaryPageEntity);
}
