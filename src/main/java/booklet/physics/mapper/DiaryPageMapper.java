package booklet.physics.mapper;

import booklet.physics.dto.DiaryPageDTO;
import booklet.physics.entity.DiaryPageEntity;

public interface DiaryPageMapper {
    DiaryPageEntity to(DiaryPageDTO diaryPageDTO);

    DiaryPageDTO from(DiaryPageEntity diaryPageEntity);
}
