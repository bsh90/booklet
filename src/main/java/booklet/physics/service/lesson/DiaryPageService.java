package booklet.physics.service.lesson;

import booklet.physics.dto.DiaryPageDTO;
import booklet.physics.entity.DiaryPageEntity;
import booklet.physics.mapper.DiaryPageMapper;
import booklet.physics.repository.DiaryPageRepository;
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
}
