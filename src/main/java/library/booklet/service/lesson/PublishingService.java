package library.booklet.service.lesson;

import library.booklet.dto.LessonPostDTO;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.QuestionSolutionEntity;
import library.booklet.repository.LessonRepository;
import library.booklet.repository.QuestionSolutionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PublishingService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    QuestionSolutionRepository questionSolutionRepository;

    public QuestionSolutionEntity postANewLesson(LessonPostDTO lessonPostDTO) {
        LessonEntity lessonEntity = new LessonEntity(lessonPostDTO.getLesson(), Collections.EMPTY_SET);
        lessonEntity = lessonRepository.saveAndFlush(lessonEntity);

        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity(lessonPostDTO.getQuestion(),
                lessonPostDTO.getAnswerOptionSolution(),
                lessonPostDTO.getSolutionDescription(),
                lessonEntity);
        questionSolutionEntity =  questionSolutionRepository.saveAndFlush(questionSolutionEntity);
        Set<QuestionSolutionEntity> questionSet = new HashSet<>();
        lessonEntity.setQuestions(questionSet);
        lessonRepository.saveAndFlush(lessonEntity);
        return questionSolutionEntity;
    }
}
