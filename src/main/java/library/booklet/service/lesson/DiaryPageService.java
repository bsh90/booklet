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

    public List<DiaryPageDTO> findAllDiaryPageDTO() {
        return diaryPageRepository.findAll()
                .stream()
                .map(e -> diaryPageMapper.from(e))
                .toList();
    }

    public DiaryPageEntity createDiaryPageDTO(DiaryPageDTO diaryPageDTO) {
        return diaryPageRepository.saveAndFlush(diaryPageMapper.to(diaryPageDTO));
    }

    public void deleteDiaryPage(Long id) {
        findDiaryPageEntity(id);
        diaryPageRepository.deleteById(id);
    }

    public Slice<DiaryPageEntity> requestDiaryPage(int pageNumber, int pageSize) {
        Pageable firstPage = PageRequest.of(pageNumber, pageSize);
        return diaryPageRepository.findAll(firstPage);
    }

    public List<DiaryPageEntity> sortDiaryPageBasedOnWrittenDat() {
        return diaryPageRepository.findAll(Sort.by("writtenDate"));
    }
}
