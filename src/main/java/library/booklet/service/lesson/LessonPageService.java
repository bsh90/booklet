package library.booklet.service.lesson;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.dto.QuestionPostDTO;
import library.booklet.dto.LessonUserAnswerDTO;
import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.QuestionSolutionEntity;
import library.booklet.exception.EntityNotFoundException;
import library.booklet.mapper.DiaryPageMapper;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    DiaryPageMapper diaryPageMapper;

    @Autowired
    QuestionSolutionMapper questionSolutionMapper;

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

    public LessonDTO postNewLesson(LessonPostDTO lessonPostDTO) {
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setEntry(lessonPostDTO.getLesson());

        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity();
        questionSolutionEntity.setQuestion(lessonPostDTO.getInitialQuestion());
        questionSolutionEntity.setOptions(lessonPostDTO.getAnswerOptionOfInitialQuestion());
        questionSolutionEntity.setOptionSolution(lessonPostDTO.getAnswerOptionSolutionOfInitialQuestion());
        questionSolutionEntity.setDescription(lessonPostDTO.getSolutionDescriptionOfInitialQuestion());
        questionSolutionEntity.setLesson(lessonEntity);

        Set<QuestionSolutionEntity> questionSet = new HashSet<>();
        questionSet.add(questionSolutionEntity);
        lessonEntity.setQuestions(questionSet);
        lessonEntity = lessonRepository.saveAndFlush(lessonEntity);

        return lessonMapper.from(lessonEntity);
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    public void deleteQuestion(Long id) {
        questionSolutionRepository.deleteById(id);
    }

    public LessonDTO updateLesson(LessonDTO lessonDTO) {
        Optional<LessonEntity> lessonEntityOptional = lessonRepository.findById(lessonDTO.getId());
        if (lessonEntityOptional.isPresent()) {
            LessonEntity lessonEntity = lessonMapper.to(lessonDTO);
            LessonEntity updatedLessonEntity = lessonRepository.saveAndFlush(lessonEntity);

            return lessonMapper.from(updatedLessonEntity);
        } else {
            throw new EntityNotFoundException("Lesson not found.");
        }
    }

    public QuestionSolutionDTO postNewQuestion(QuestionPostDTO questionPostDTO) {
        LessonEntity lessonEntity = lessonRepository.findById(questionPostDTO.getLessonId())
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity();
        questionSolutionEntity.setQuestion(questionPostDTO.getQuestion());
        questionSolutionEntity.setOptions(questionPostDTO.getAnswerOption());
        questionSolutionEntity.setOptionSolution(questionPostDTO.getAnswerOptionSolution());
        questionSolutionEntity.setDescription(questionPostDTO.getSolutionDescription());
        questionSolutionEntity.setLesson(lessonEntity);
        questionSolutionEntity = questionSolutionRepository.saveAndFlush(questionSolutionEntity);
        return questionSolutionMapper.from(questionSolutionEntity);
    }

    public DiaryPageDTO postNewAnswer(LessonUserAnswerDTO lessonAnswerDTO) {
        boolean result = evaluateAnswer(lessonAnswerDTO);
        return postAnswerCommentary(result, lessonAnswerDTO);
    }

    private boolean evaluateAnswer(LessonUserAnswerDTO lessonAnswerDTO) {
        QuestionSolutionEntity questionSolutionEntity = questionSolutionRepository
                .findById(lessonAnswerDTO.getQuestionId())
                .orElseThrow(()->new EntityNotFoundException("Question entity not found"));

        return lessonAnswerDTO.getAnswerOption().equals(questionSolutionEntity.getOptionSolution());
    }

    private DiaryPageDTO postAnswerCommentary(boolean answerResult, LessonUserAnswerDTO lessonAnswerDTO) {
        LocalDate now = LocalDate.now();
        DiaryPageEntity diaryPageEntity = new DiaryPageEntity();
        diaryPageEntity.setWrittenDate(now);
        diaryPageEntity.setEntry("The answer is " + answerResult + ". Your Comments: " +
                lessonAnswerDTO.getAnswerDiaryEntry());
        diaryPageEntity = diaryPageRepository.saveAndFlush(diaryPageEntity);
        return diaryPageMapper.from(diaryPageEntity);
    }

    public List<LessonDTO> getAllLessons() {
        List<LessonEntity> allLessonEntities = lessonRepository.findAll();
        return allLessonEntities.stream()
                .map(entity -> lessonMapper.from(entity))
                .collect(Collectors.toList());
    }
}
