package booklet.physics.service.lesson;

import booklet.physics.dto.LessonAnswerDTO;
import booklet.physics.dto.LessonDTO;
import booklet.physics.dto.LessonSolutionDTO;
import booklet.physics.entity.DiaryPageEntity;
import booklet.physics.entity.LessonEntity;
import booklet.physics.entity.LessonSolutionEntity;
import booklet.physics.mapper.LessonMapper;
import booklet.physics.mapper.LessonSolutionMapper;
import booklet.physics.repository.DiaryPageRepository;
import booklet.physics.repository.LessonRepository;
import booklet.physics.repository.LessonSolutionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class LessonPageService {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    LessonSolutionRepository lessonSolutionRepository;

    @Autowired
    DiaryPageRepository diaryPageRepository;

    @Autowired
    LessonMapper lessonMapper;

    @Autowired
    LessonSolutionMapper lessonSolutionMapper;

    public LessonDTO getLessonDTO(Long id) {
        LessonEntity lessonEntity = lessonRepository.getReferenceById(id);
        return lessonMapper.from(lessonEntity);
    }

    public LessonSolutionDTO getLessonSolutionDTO(Long lessonPageEntityId) {
        LessonSolutionEntity lessonSolutionEntity = lessonSolutionRepository.findByLessonPageEntityId(lessonPageEntityId);
        return lessonSolutionMapper.from(lessonSolutionEntity);
    }

    public boolean evaluateAnswer(LessonAnswerDTO lessonAnswerDTO) {
        LessonSolutionEntity lessonSolutionEntity = lessonSolutionRepository.findByLessonPageEntityId(
                lessonAnswerDTO.getLessonPageEntityId());

        return lessonAnswerDTO.getAnswerOption().equals(lessonSolutionEntity.getOption());
    }

    public void postAnswerCommentary(LessonAnswerDTO lessonAnswerDTO) {
        LocalDate now = LocalDate.now();
        DiaryPageEntity diaryPageEntity = new DiaryPageEntity(now, lessonAnswerDTO.getAnswerCommentary());
        diaryPageRepository.saveAndFlush(diaryPageEntity);
    }
}
