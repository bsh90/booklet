package booklet.physics.mapper;

import booklet.physics.dto.LessonAnswerDTO;
import booklet.physics.entity.LessonAnswerEntity;


public interface LessonAnswerMapper {

    LessonAnswerEntity to(LessonAnswerDTO lessonPageDTO);

    LessonAnswerDTO from(LessonAnswerEntity lessonPageEntity);
}
