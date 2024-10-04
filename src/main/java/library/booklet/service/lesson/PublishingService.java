package library.booklet.service.lesson;

import library.booklet.dto.LessonPostDTO;
import library.booklet.dto.QuestionPostDTO;
import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.QuestionSolutionEntity;
import library.booklet.exception.EntityNotFoundException;
import library.booklet.mapper.QuestionSolutionMapper;
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

    @Autowired
    QuestionSolutionMapper questionSolutionMapper;

    public QuestionSolutionDTO postNewLesson(LessonPostDTO lessonPostDTO) {
        LessonEntity lessonEntity = new LessonEntity(lessonPostDTO.getLesson(), Collections.EMPTY_SET);
        lessonEntity = lessonRepository.saveAndFlush(lessonEntity);

        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity(lessonPostDTO.getInitialQuestion(),
                lessonPostDTO.getAnswerOptionOfInitialQuestion(),
                lessonPostDTO.getAnswerOptionSolutionOfInitialQuestion(),
                lessonPostDTO.getSolutionDescriptionOfInitialQuestion(),
                lessonEntity);
        questionSolutionEntity =  questionSolutionRepository.saveAndFlush(questionSolutionEntity);

        Set<QuestionSolutionEntity> questionSet = new HashSet<>();
        lessonEntity.setQuestions(questionSet);
        lessonRepository.saveAndFlush(lessonEntity);

        return questionSolutionMapper.from(questionSolutionEntity);
    }

    public QuestionSolutionDTO postNewQuestion(QuestionPostDTO questionPostDTO) {
        LessonEntity lessonEntity = lessonRepository.findById(questionPostDTO.getLessonId())
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity(questionPostDTO.getQuestion(),
                questionPostDTO.getAnswerOption(),
                questionPostDTO.getAnswerOptionSolution(),
                questionPostDTO.getSolutionDescription(),
                lessonEntity);
        return questionSolutionMapper.from(questionSolutionEntity);
    }
}
