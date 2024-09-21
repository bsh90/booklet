package library.booklet.service.lesson;

import library.booklet.dto.LessonAnswerDTO;
import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonSolutionDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.LessonSolutionEntity;
import library.booklet.mapper.LessonMapper;
import library.booklet.mapper.LessonSolutionMapper;
import library.booklet.repository.DiaryPageRepository;
import library.booklet.repository.LessonRepository;
import library.booklet.repository.LessonSolutionRepository;
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
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson id not found - " + id));
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

    public DiaryPageEntity postAnswerCommentary(LessonAnswerDTO lessonAnswerDTO) {
        LocalDate now = LocalDate.now();
        DiaryPageEntity diaryPageEntity = new DiaryPageEntity(now, lessonAnswerDTO.getAnswerCommentary());
        return diaryPageRepository.saveAndFlush(diaryPageEntity);
    }
}
