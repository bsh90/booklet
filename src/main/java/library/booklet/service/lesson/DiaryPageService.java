package library.booklet.service.lesson;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.mapper.DiaryPageMapper;
import library.booklet.repository.DiaryPageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new RuntimeException("DiaryPage id not found - " + id));
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
}
