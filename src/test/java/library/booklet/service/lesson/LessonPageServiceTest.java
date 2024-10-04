package library.booklet.service.lesson;

import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.dto.LessonUserAnswerDTO;
import library.booklet.dto.LessonDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.QuestionSolutionEntity;
import library.booklet.mapper.LessonMapper;
import library.booklet.mapper.QuestionSolutionMapper;
import library.booklet.repository.DiaryPageRepository;
import library.booklet.repository.LessonRepository;
import library.booklet.repository.QuestionSolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LessonPageServiceTest {

    LessonPageService lessonPageService;
    LessonRepository lessonRepository;
    QuestionSolutionRepository questionSolutionRepository;
    DiaryPageRepository diaryPageRepository;
    LessonMapper lessonMapper;
    QuestionSolutionMapper lessonSolutionMapper;

    @BeforeEach
    void setUp() {
        lessonRepository = mock(LessonRepository.class);
        questionSolutionRepository = mock(QuestionSolutionRepository.class);
        diaryPageRepository = mock(DiaryPageRepository.class);
        lessonMapper = mock(LessonMapper.class);
        lessonSolutionMapper = mock(QuestionSolutionMapper.class);
        lessonPageService = new LessonPageService(lessonRepository,
                questionSolutionRepository,
                diaryPageRepository,
                lessonMapper,
                lessonSolutionMapper);
    }

    @Test
    void getLessonDTO() {
        Long sampleId = 1L;
        LessonEntity lessonEntity = new LessonEntity();
        LessonDTO lessonDTO = new LessonDTO();
        stub_getLessonDTO(sampleId, lessonEntity, lessonDTO);

        LessonDTO result = lessonPageService.getLessonDTO(sampleId);
        assertThat(result).isEqualTo(lessonDTO);
    }

    void stub_getLessonDTO(Long id, LessonEntity lessonEntity, LessonDTO lessonDTO) {
        when(lessonRepository.findById(id)).thenReturn(Optional.ofNullable(lessonEntity));
        when(lessonMapper.from(lessonEntity)).thenReturn(lessonDTO);
    }

    @Test
    void getLessonSolutionDTO() {
        Long sampleId = 1L;
        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity();
        QuestionSolutionDTO questionSolutionDTO = new QuestionSolutionDTO();
        stub_getLessonSolutionDTO(sampleId, questionSolutionEntity, questionSolutionDTO);

        QuestionSolutionDTO result = lessonPageService.getLessonSolutionDTO(sampleId);
        assertThat(result).isEqualTo(questionSolutionDTO);
    }

    void stub_getLessonSolutionDTO(Long id, QuestionSolutionEntity questionSolutionEntity, QuestionSolutionDTO questionSolutionDTO) {
        when(questionSolutionRepository.findById(id)).thenReturn(Optional.ofNullable(questionSolutionEntity));
        when(lessonSolutionMapper.from(questionSolutionEntity)).thenReturn(questionSolutionDTO);
    }

    @Test
    void evaluateAnswer() {
        // meed database
    }

    @Test
    void postAnswerCommentary() {
        LessonUserAnswerDTO inputLessonUserAnswerDTO = new LessonUserAnswerDTO();
        String commentary = "commentary";
        inputLessonUserAnswerDTO.setAnswerCommentary(commentary);

        DiaryPageEntity diaryPageEntity = new DiaryPageEntity(LocalDate.of(2024, 9, 9), commentary);
        when(diaryPageRepository.saveAndFlush(any())).thenReturn(diaryPageEntity);

        DiaryPageEntity result = lessonPageService.postAnswerCommentary(inputLessonUserAnswerDTO);
        assertThat(result).isEqualTo(diaryPageEntity);
    }
}