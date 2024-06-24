package booklet.physics.mapper;

import booklet.physics.dto.LessonDTO;
import booklet.physics.entity.LessonEntity;


public interface LessonMapper {

    LessonEntity to(LessonDTO lessonDTO);

    LessonDTO from(LessonEntity lessonEntity);
}
