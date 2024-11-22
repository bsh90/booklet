package library.booklet.mapper;

import library.booklet.dto.UserDTO;
import library.booklet.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target="id", ignore = true)
    UserEntity to(UserDTO diaryPageDTO);

    UserDTO from(UserEntity diaryPageEntity);
}
