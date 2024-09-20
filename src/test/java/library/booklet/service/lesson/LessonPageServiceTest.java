package library.booklet.service.lesson;

import library.booklet.dto.LessonAnswerDTO;
import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonSolutionDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.LessonSolutionEntity;
import library.booklet.mapper.LessonMapper;
import library.booklet.mapper.LessonSolutionMapper;
import library.booklet.repository.DiaryPageRepository;
import library.booklet.repository.LessonRepository;
import library.booklet.repository.LessonSolutionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LessonPageServiceTest {

    LessonPageService lessonPageService;
    LessonRepository lessonRepository;
    LessonSolutionRepository lessonSolutionRepository;
    DiaryPageRepository diaryPageRepository;
    LessonMapper lessonMapper;
    LessonSolutionMapper lessonSolutionMapper;

    @BeforeEach
    void setUp() {
        lessonRepository = mock(LessonRepository.class);
        lessonSolutionRepository = mock(LessonSolutionRepository.class);
        diaryPageRepository = mock(DiaryPageRepository.class);
        lessonMapper = mock(LessonMapper.class);
        lessonSolutionMapper = mock(LessonSolutionMapper.class);
        lessonPageService = new LessonPageService(lessonRepository,
                lessonSolutionRepository,
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
        when(lessonRepository.getReferenceById(id)).thenReturn(lessonEntity);
        when(lessonMapper.from(lessonEntity)).thenReturn(lessonDTO);
    }

    @Test
    void getLessonSolutionDTO() {
        Long sampleId = 1L;
        LessonSolutionEntity lessonSolutionEntity = new LessonSolutionEntity();
        LessonSolutionDTO lessonSolutionDTO = new LessonSolutionDTO();
        stub_getLessonSolutionDTO(sampleId, lessonSolutionEntity, lessonSolutionDTO);

        LessonSolutionDTO result = lessonPageService.getLessonSolutionDTO(sampleId);
        assertThat(result).isEqualTo(lessonSolutionDTO);
    }

    void stub_getLessonSolutionDTO(Long id, LessonSolutionEntity lessonSolutionEntity, LessonSolutionDTO lessonSolutionDTO) {
        when(lessonSolutionRepository.findByLessonPageEntityId(id)).thenReturn(lessonSolutionEntity);
        when(lessonSolutionMapper.from(lessonSolutionEntity)).thenReturn(lessonSolutionDTO);
    }

    @Test
    void evaluateAnswer() {
        // meed database
    }

    @Test
    void postAnswerCommentary() {
        LessonAnswerDTO inputLessonAnswerDTO = new LessonAnswerDTO();
        String commentary = "commentary";
        inputLessonAnswerDTO.setAnswerCommentary(commentary);

        DiaryPageEntity diaryPageEntity = new DiaryPageEntity(LocalDate.of(2024, 9, 9), commentary);
        when(diaryPageRepository.saveAndFlush(any())).thenReturn(diaryPageEntity);

        DiaryPageEntity result = lessonPageService.postAnswerCommentary(inputLessonAnswerDTO);
        assertThat(result).isEqualTo(diaryPageEntity);
    }
}