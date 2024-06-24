package booklet.physics.service.lesson;

import booklet.physics.dto.LessonPostDTO;
import booklet.physics.entity.LessonEntity;
import booklet.physics.entity.LessonSolutionEntity;
import booklet.physics.repository.LessonRepository;
import booklet.physics.repository.LessonSolutionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PublishingService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    LessonSolutionRepository lessonSolutionRepository;

    public void postANewLesson(LessonPostDTO lessonPostDTO) {
        LessonEntity lessonEntity = new LessonEntity(lessonPostDTO.getLesson(),
                                                    lessonPostDTO.getQuestion(),
                                                    lessonPostDTO.getAnswerOption());
        lessonEntity = lessonRepository.saveAndFlush(lessonEntity);

        LessonSolutionEntity lessonSolutionEntity = new LessonSolutionEntity(lessonPostDTO.getAnswerOptionSolution(),
                                                                            lessonPostDTO.getSolutionDescription(),
                                                                            lessonEntity.getId());
        lessonSolutionRepository.saveAndFlush(lessonSolutionEntity);
    }
}
