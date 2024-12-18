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
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> getDiaryPageDTO(Long id) {
        try {
            DiaryPageEntity diaryPageEntity = findDiaryPageEntity(id);
            DiaryPageDTO diaryPageDTO = diaryPageMapper.from(diaryPageEntity);
            return ResponseEntity.ok(diaryPageDTO);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
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

    public ResponseEntity<?> deleteDiaryPage(Long id) {
        try{
            findDiaryPageEntity(id);
            diaryPageRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch(EntityNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    public List<DiaryPageEntity> requestDiaryPagesSortByWrittenDate(int pageIndex, int pageSize) {
        Pageable firstPage = PageRequest.of(pageIndex, pageSize, Sort.by(new Sort.Order(Sort.Direction.ASC,"writtenDate")));
        Slice<DiaryPageEntity> pageSlice = diaryPageRepository.findAll(firstPage);
        return pageSlice.getContent();
    }

    public List<DiaryPageEntity> sortACSDiaryPageBasedOnWrittenDate() {
        return diaryPageRepository.findAll(Sort.by(Sort.Direction.ASC,"writtenDate"));
    }
}
