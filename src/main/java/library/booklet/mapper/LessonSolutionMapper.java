package library.booklet.mapper;

import library.booklet.dto.LessonSolutionDTO;
import library.booklet.entity.LessonSolutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LessonSolutionMapper {

    LessonSolutionEntity to(LessonSolutionDTO lessonDTO);

    LessonSolutionDTO from(LessonSolutionEntity lessonEntity);
}
