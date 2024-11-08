package library.booklet.mapper;

import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.entity.QuestionSolutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionSolutionMapper {

    @Mapping(target="lesson", ignore = true)
    QuestionSolutionEntity to(QuestionSolutionDTO lessonDTO);

    QuestionSolutionDTO from(QuestionSolutionEntity lessonEntity);
}
