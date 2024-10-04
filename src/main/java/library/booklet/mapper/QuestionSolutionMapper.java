package library.booklet.mapper;

import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.entity.QuestionSolutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionSolutionMapper {

    @Mapping(source="lesson", target="lesson")
    QuestionSolutionEntity to(QuestionSolutionDTO lessonDTO);

    @Mapping(source="lesson", target="lesson")
    QuestionSolutionDTO from(QuestionSolutionEntity lessonEntity);
}
