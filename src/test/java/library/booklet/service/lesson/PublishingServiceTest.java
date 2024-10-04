package library.booklet.service.lesson;

import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
import library.booklet.dto.QuestionPostDTO;
import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.QuestionSolutionEntity;
import library.booklet.mapper.QuestionSolutionMapper;
import library.booklet.repository.LessonRepository;
import library.booklet.repository.QuestionSolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PublishingServiceTest {

    PublishingService publishingService;
    LessonRepository lessonRepository;
    QuestionSolutionRepository questionSolutionRepository;
    QuestionSolutionMapper questionSolutionMapper;

    @BeforeEach
    void setUp() {
        lessonRepository = mock(LessonRepository.class);
        questionSolutionRepository = mock(QuestionSolutionRepository.class);
        questionSolutionMapper = mock(QuestionSolutionMapper.class);
        publishingService = new PublishingService(lessonRepository, questionSolutionRepository, questionSolutionMapper);
    }

    @Test
    void postNewLesson() {
        QuestionSolutionEntity questionEntity = new QuestionSolutionEntity();
        LocalDate createdAt = LocalDate.now();
        LocalDate updatedAt = null;
        String lesson = "lesson";
        String question = "question";
        String answerOptionSolution = "answerOptionSolution";
        List<String> answerOptions = asList(answerOptionSolution, "option2", "option3");
        String solutionDescription = "solutionDescription";

        LessonPostDTO lessonPostDTO = new LessonPostDTO(lesson, question, answerOptions, answerOptionSolution,
                solutionDescription);
        LessonEntity lessonEntity = new LessonEntity(lesson, Collections.emptySet());
        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity(question, answerOptions,
                answerOptionSolution, solutionDescription, lessonEntity);
        Set<QuestionSolutionEntity> questionSet = new HashSet<>();
        questionSet.add(questionSolutionEntity);
        lessonEntity.setQuestions(questionSet);
        LessonDTO lessonDTO = new LessonDTO(lessonEntity.getId(),
                lessonEntity.getCreatedAt(),
                lessonEntity.getCreatedAt(),
                lessonEntity.getEntry(),
                Collections.emptySet());
        QuestionSolutionDTO questionSolutionDTO = new QuestionSolutionDTO(questionSolutionEntity.getId(),
                questionSolutionEntity.getCreatedAt(),
                questionSolutionEntity.getUpdatedAt(),
                questionSolutionEntity.getQuestion(),
                questionSolutionEntity.getOptions(),
                questionSolutionEntity.getOptionSolution(),
                questionSolutionEntity.getDescription(),
                lessonDTO);
        Set<QuestionSolutionDTO> questionSolutionDTOs = new HashSet<>();
        questionSolutionDTOs.add(questionSolutionDTO);
        lessonDTO.setQuestions(questionSolutionDTOs);

        stub_postNewLesson(lessonEntity, questionSolutionEntity, questionSolutionDTO);

        QuestionSolutionDTO result = publishingService.postNewLesson(lessonPostDTO);
        assertThat(result).isEqualTo(questionSolutionDTO);
        // test of find in database here as well
    }

    void stub_postNewLesson(LessonEntity lessonEntity, QuestionSolutionEntity questionSolutionEntity,
                             QuestionSolutionDTO questionSolutionDTO) {
        when(lessonRepository.saveAndFlush(any())).thenReturn(lessonEntity);
        when(questionSolutionRepository.saveAndFlush(any())).thenReturn(questionSolutionEntity);
        when(questionSolutionMapper.from(eq(questionSolutionEntity))).thenReturn(questionSolutionDTO);
    }

    @Test
    void postNewQuestion() {
        Long id = 1L;
        String question = "question";
        String answerOptionSolution = "answerOptionSolution";
        List<String> answerOptions = asList(answerOptionSolution, "option2", "option3");
        String solutionDescription = "solutionDescription";
        String entry = "entry";

        QuestionPostDTO questionPostDTO = new QuestionPostDTO(id, question,
                answerOptions, answerOptionSolution, solutionDescription);
        LessonEntity lessonEntity = new LessonEntity(entry, Collections.emptySet());
        QuestionSolutionDTO questionSolutionDTO = new QuestionSolutionDTO();
        stub_postNewQuestion(id, lessonEntity, questionSolutionDTO);

        QuestionSolutionDTO result = publishingService.postNewQuestion(questionPostDTO);
        assertThat(result).isEqualTo(questionSolutionDTO);
    }

    void stub_postNewQuestion(Long lessonId, LessonEntity lessonEntity, QuestionSolutionDTO questionSolutionDTO) {
        when(lessonRepository.findById(eq(lessonId))).thenReturn(Optional.ofNullable(lessonEntity));
        when(questionSolutionMapper.from(any())).thenReturn(questionSolutionDTO);
    }
}