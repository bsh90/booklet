package library.booklet.service.lesson;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.mapper.DiaryPageMapper;
import library.booklet.repository.DiaryPageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class DiaryPageService {

    @Autowired
    DiaryPageRepository diaryPageRepository;

    @Autowired
    DiaryPageMapper diaryPageMapper;

    public DiaryPageDTO getDiaryPageDTO(Long id) {
        DiaryPageEntity diaryPageEntity = diaryPageRepository.getReferenceById(id);
        return diaryPageMapper.from(diaryPageEntity);
    }

    public DiaryPageEntity createDiaryPageDTO(DiaryPageDTO diaryPageDTO) {
        return diaryPageRepository.saveAndFlush(diaryPageMapper.to(diaryPageDTO));
    }
}
