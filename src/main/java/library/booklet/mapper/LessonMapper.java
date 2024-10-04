package library.booklet.mapper;

import library.booklet.dto.LessonDTO;
import library.booklet.entity.LessonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonMapper {

    @Mapping(source="questions", target="questions")
    LessonEntity to(LessonDTO lessonDTO);

    @Mapping(source="questions", target="questions")
    LessonDTO from(LessonEntity lessonEntity);
}
