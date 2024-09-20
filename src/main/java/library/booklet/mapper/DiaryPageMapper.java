package library.booklet.mapper;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DiaryPageMapper {
    DiaryPageEntity to(DiaryPageDTO diaryPageDTO);

    DiaryPageDTO from(DiaryPageEntity diaryPageEntity);
}
