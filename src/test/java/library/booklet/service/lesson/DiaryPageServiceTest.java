package library.booklet.service.lesson;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.mapper.DiaryPageMapper;
import library.booklet.repository.DiaryPageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
    void getDiaryPageDTO() {
        Long sampleId = 1L;
        DiaryPageEntity diaryPageEntity = new DiaryPageEntity();
        DiaryPageDTO expectedDiaryPageDTO = new DiaryPageDTO();
        stub_getDiaryPageDTO(sampleId, diaryPageEntity, expectedDiaryPageDTO);

        DiaryPageDTO result = diaryPageService.getDiaryPageDTO(sampleId);
        assertThat(result).isEqualTo(expectedDiaryPageDTO);
    }

    private void stub_getDiaryPageDTO(Long sampleId,
                                      DiaryPageEntity returnDiaryPageEntity,
                                      DiaryPageDTO diaryPageDTO) {
        when(diaryPageRepository.getReferenceById(eq(sampleId))).thenReturn(returnDiaryPageEntity);
        when(diaryPageMapper.from(eq(returnDiaryPageEntity))).thenReturn(diaryPageDTO);
    }

    @Test
    void createDiaryPageDTO() {

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
}