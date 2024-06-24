package booklet.physics.mapper;

import booklet.physics.dto.LessonDTO;
import booklet.physics.dto.LessonSolutionDTO;
import booklet.physics.entity.LessonEntity;
import booklet.physics.entity.LessonSolutionEntity;


public interface LessonSolutionMapper {

    LessonSolutionEntity to(LessonSolutionDTO lessonDTO);

    LessonSolutionDTO from(LessonSolutionEntity lessonEntity);
}
