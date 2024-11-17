package library.booklet.service.lesson;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.exception.EntityNotFoundException;
import library.booklet.mapper.DiaryPageMapper;
import library.booklet.repository.DiaryPageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DiaryPageService {

    @Autowired
    DiaryPageRepository diaryPageRepository;

    @Autowired
    DiaryPageMapper diaryPageMapper;

    public DiaryPageDTO getDiaryPageDTO(Long id) {
        DiaryPageEntity diaryPageEntity = findDiaryPageEntity(id);
        return diaryPageMapper.from(diaryPageEntity);
    }

    private DiaryPageEntity findDiaryPageEntity(Long id) {
        return diaryPageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DiaryPage id not found - " + id));
    }

    public List<DiaryPageDTO> getDiaryOfDate(String writtenDateString) {
        LocalDate writtenDate = LocalDate.parse(writtenDateString);
        Collection<DiaryPageEntity> diaryEntities = diaryPageRepository.findByWrittenDate(writtenDate);
        return diaryEntities.stream()
                .map(entity -> diaryPageMapper.from(entity))
                .toList();
    }

    public List<DiaryPageDTO> findAllDiaryPageDTO() {
        return diaryPageRepository.findAll()
                .stream()
                .map(e -> diaryPageMapper.from(e))
                .toList();
    }

    public List<DiaryPageEntity> findAllDiaryPageEntity() {
        return diaryPageRepository.findAll();
    }

    public DiaryPageEntity createDiaryPageDTO(DiaryPageDTO diaryPageDTO) {
        return diaryPageRepository.saveAndFlush(diaryPageMapper.to(diaryPageDTO));
    }

    public void deleteDiaryPage(Long id) {
        findDiaryPageEntity(id);
        diaryPageRepository.deleteById(id);
    }

    public List<DiaryPageEntity> requestDiaryPagesSortByWrittenDate(int pageNumber, int pageSize) {
        Pageable firstPage = PageRequest.of(pageNumber, pageSize, Sort.by("writtenDate"));
        Slice<DiaryPageEntity> pageSlice = diaryPageRepository.findAll(firstPage);
        return pageSlice.getContent();
    }

    public List<DiaryPageEntity> sortACSDiaryPageBasedOnWrittenDate() {
        return diaryPageRepository.findAll(Sort.by(Sort.Direction.ASC,"writtenDate"));
    }
}
