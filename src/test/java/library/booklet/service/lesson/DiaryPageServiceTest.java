package library.booklet.service.lesson;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.mapper.DiaryPageMapper;
import library.booklet.repository.DiaryPageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DiaryPageServiceTest {

    DiaryPageService diaryPageService;
    DiaryPageRepository diaryPageRepository;
    DiaryPageMapper diaryPageMapper;

    @BeforeEach
    void setUp() {
        diaryPageRepository = mock(DiaryPageRepository.class);
        diaryPageMapper = mock(DiaryPageMapper.class);
        diaryPageService = new DiaryPageService(diaryPageRepository, diaryPageMapper);
    }

    @Test
    void happyPath_getDiaryPageDTO() {
        Long sampleId = 1L;
        DiaryPageEntity diaryPageEntity = new DiaryPageEntity();
        DiaryPageDTO expectedDiaryPageDTO = new DiaryPageDTO();
        ResponseEntity<?> response = ResponseEntity.ok(expectedDiaryPageDTO);
        stub_getDiaryPageDTO(sampleId, diaryPageEntity, expectedDiaryPageDTO);

        ResponseEntity<?> result = diaryPageService.getDiaryPageDTO(sampleId);
        assertThat(result).isEqualTo(response);
    }

    private void stub_getDiaryPageDTO(Long sampleId,
                                      DiaryPageEntity returnDiaryPageEntity,
                                      DiaryPageDTO diaryPageDTO) {
        when(diaryPageRepository.findById(eq(sampleId))).thenReturn(Optional.ofNullable(returnDiaryPageEntity));
        when(diaryPageMapper.from(eq(returnDiaryPageEntity))).thenReturn(diaryPageDTO);
    }

    @Test
    void happyPath_getDiaryOfDate() {
        String writtenDateString = "2024-11-15";

        DiaryPageEntity diaryPage = new DiaryPageEntity(1L,
                LocalDate.of(2024, 11, 15),
                "entry");
        DiaryPageDTO diaryPageDTO = new DiaryPageDTO(
                LocalDate.of(2024, 11, 15),
                "entry");
        stub_happyPath_getDiaryOfDate(diaryPage, diaryPageDTO);
        List<DiaryPageDTO> expectedResult = List.of(diaryPageDTO);

        List<DiaryPageDTO> result = diaryPageService.getDiaryOfDate(writtenDateString);
        assertThat(result).isEqualTo(expectedResult);
    }

    private void stub_happyPath_getDiaryOfDate(DiaryPageEntity diaryPage, DiaryPageDTO diaryPageDTO) {
        Collection<DiaryPageEntity> diaryEntities = Collections.singleton(diaryPage);
        when(diaryPageRepository.findByWrittenDate(any())).thenReturn(diaryEntities);
        when(diaryPageMapper.from(eq(diaryPage))).thenReturn(diaryPageDTO);
    }

    @Test
    void happyPath_createDiaryPageDTO() {

        DiaryPageDTO inputDiaryPageDTO = new DiaryPageDTO();
        DiaryPageEntity diaryPageEntity = new DiaryPageEntity();
        stub_diaryCreateDiaryPageDTO(inputDiaryPageDTO, diaryPageEntity);

        DiaryPageEntity result = diaryPageService.createDiaryPageDTO(inputDiaryPageDTO);
        assertThat(result).isEqualTo(diaryPageEntity);
    }

    private void stub_diaryCreateDiaryPageDTO(DiaryPageDTO inputDiaryPageDTO, DiaryPageEntity diaryPageEntity) {
        when(diaryPageMapper.to(inputDiaryPageDTO)).thenReturn(diaryPageEntity);
        when(diaryPageRepository.saveAndFlush(diaryPageEntity)).thenReturn(diaryPageEntity);
    }

    @Test
    void happyPath_sortACSDiaryPageBasedOnWrittenDate() {

        DiaryPageEntity diaryPageEntity1 = new DiaryPageEntity(1L,
                LocalDate.of(2024, 11, 15), "entry1");
        DiaryPageEntity diaryPageEntity2 = new DiaryPageEntity(2L,
                LocalDate.of(2024, 11, 16), "entry2");
        List<DiaryPageEntity> diaryPageEntities = List.of(diaryPageEntity1, diaryPageEntity2);
        when(diaryPageRepository.findAll(Sort.by(Sort.Direction.ASC,"writtenDate"))).thenReturn(diaryPageEntities);

        List<DiaryPageEntity> result = diaryPageService.sortACSDiaryPageBasedOnWrittenDate();
        assertThat(result).isEqualTo(diaryPageEntities);
    }
}