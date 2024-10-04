package library.booklet.service.lesson;

import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.dto.LessonUserAnswerDTO;
import library.booklet.dto.LessonDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.QuestionSolutionEntity;
import library.booklet.exception.EntityNotFoundException;
import library.booklet.mapper.LessonMapper;
import library.booklet.mapper.QuestionSolutionMapper;
import library.booklet.repository.DiaryPageRepository;
import library.booklet.repository.LessonRepository;
import library.booklet.repository.QuestionSolutionRepository;
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
    QuestionSolutionRepository questionSolutionRepository;

    @Autowired
    DiaryPageRepository diaryPageRepository;

    @Autowired
    LessonMapper lessonMapper;

    @Autowired
    QuestionSolutionMapper lessonSolutionMapper;

    public LessonDTO getLessonDTO(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson id not found - " + id));
        return lessonMapper.from(lessonEntity);
    }

    public QuestionSolutionDTO getLessonSolutionDTO(Long lessonPageEntityId) {
        QuestionSolutionEntity questionSolutionEntity = questionSolutionRepository.findById(lessonPageEntityId)
                .orElseThrow(()-> new EntityNotFoundException("Question Entity not found"));
        return lessonSolutionMapper.from(questionSolutionEntity);
    }

    public boolean evaluateAnswer(LessonUserAnswerDTO lessonAnswerDTO) {
        QuestionSolutionEntity questionSolutionEntity = questionSolutionRepository
                .findById(lessonAnswerDTO.getQuestionId())
                .orElseThrow(()->new EntityNotFoundException("Question entity not found"));

        return lessonAnswerDTO.getAnswerOption().equals(questionSolutionEntity.getOption());
    }

    public DiaryPageEntity postAnswerCommentary(LessonUserAnswerDTO lessonAnswerDTO) {
        LocalDate now = LocalDate.now();
        DiaryPageEntity diaryPageEntity = new DiaryPageEntity(now, lessonAnswerDTO.getAnswerCommentary());
        return diaryPageRepository.saveAndFlush(diaryPageEntity);
    }
}
