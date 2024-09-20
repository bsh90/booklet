package library.booklet.mapper;

import library.booklet.dto.AccountProfileDTO;
import library.booklet.entity.AccountProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountProfileMapper {
    AccountProfileEntity to(AccountProfileDTO diaryPageDTO);

    AccountProfileDTO from(AccountProfileEntity diaryPageEntity);
}
