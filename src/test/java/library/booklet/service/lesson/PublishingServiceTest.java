package library.booklet.service.lesson;

import library.booklet.dto.LessonPostDTO;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.QuestionSolutionEntity;
import library.booklet.repository.LessonRepository;
import library.booklet.repository.QuestionSolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PublishingServiceTest {

    PublishingService publishingService;
    LessonRepository lessonRepository;
    QuestionSolutionRepository questionSolutionRepository;

    @BeforeEach
    void setUp() {
        lessonRepository = mock(LessonRepository.class);
        questionSolutionRepository = mock(QuestionSolutionRepository.class);
        publishingService = new PublishingService(lessonRepository, questionSolutionRepository);
    }

    @Test
    void postANewLesson() {
        QuestionSolutionEntity questionEntity = new QuestionSolutionEntity();
        LocalDate createdAt = LocalDate.now();
        LocalDate updatedAt = null;
        String lesson = "lesson";
        String question = "question";
        String answerOption = "answerOption";
        String answerOptionSolution = "answerOptionSolution";
        String solutionDescription = "solutionDescription";

        LessonPostDTO lessonPostDTO = new LessonPostDTO(lesson, question, answerOption, answerOptionSolution,
                solutionDescription);
        LessonEntity lessonEntity = new LessonEntity(lesson, Collections.emptySet());
        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity(question,
                answerOption, solutionDescription, lessonEntity);
        Set<QuestionSolutionEntity> questionSet = new HashSet<>();
        questionSet.add(questionSolutionEntity);
        lessonEntity.setQuestions(questionSet);
        stub_postANewLesson(lessonEntity, questionSolutionEntity);

        QuestionSolutionEntity result = publishingService.postANewLesson(lessonPostDTO);
        assertThat(result).isEqualTo(questionSolutionEntity);
        // test of find in database here as well
    }

    void stub_postANewLesson(LessonEntity lessonEntity, QuestionSolutionEntity questionSolutionEntity) {
        when(lessonRepository.saveAndFlush(any())).thenReturn(lessonEntity);
        when(questionSolutionRepository.saveAndFlush(any())).thenReturn(questionSolutionEntity);
    }
}