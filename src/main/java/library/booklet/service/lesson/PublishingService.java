package library.booklet.service.lesson;

import library.booklet.dto.LessonPostDTO;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.LessonSolutionEntity;
import library.booklet.repository.LessonRepository;
import library.booklet.repository.LessonSolutionRepository;
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

    public LessonSolutionEntity postANewLesson(LessonPostDTO lessonPostDTO) {
        LessonEntity lessonEntity = new LessonEntity(lessonPostDTO.getLesson(),
                                                    lessonPostDTO.getQuestion(),
                                                    lessonPostDTO.getAnswerOption());
        lessonEntity = lessonRepository.saveAndFlush(lessonEntity);

        LessonSolutionEntity lessonSolutionEntity = new LessonSolutionEntity(lessonPostDTO.getAnswerOptionSolution(),
                                                                            lessonPostDTO.getSolutionDescription(),
                                                                            lessonEntity.getId());
        return lessonSolutionRepository.saveAndFlush(lessonSolutionEntity);
    }
}
