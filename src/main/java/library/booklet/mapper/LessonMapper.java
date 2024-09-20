package library.booklet.mapper;

import library.booklet.dto.LessonDTO;
import library.booklet.entity.LessonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonMapper {

    LessonEntity to(LessonDTO lessonDTO);

    LessonDTO from(LessonEntity lessonEntity);
}
