package library.booklet.mapper;

import library.booklet.dto.LessonAnswerDTO;
import library.booklet.entity.LessonAnswerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonAnswerMapper {

    LessonAnswerEntity to(LessonAnswerDTO lessonPageDTO);

    LessonAnswerDTO from(LessonAnswerEntity lessonPageEntity);
}
