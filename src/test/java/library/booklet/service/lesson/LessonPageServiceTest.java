package library.booklet.service.lesson;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
import library.booklet.dto.LessonUserAnswerDTO;
import library.booklet.dto.QuestionPostDTO;
import library.booklet.dto.QuestionSolutionDTO;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LessonPageServiceTest {

    LessonPageService lessonPageService;
    LessonRepository lessonRepository;
    QuestionSolutionRepository questionSolutionRepository;
    DiaryPageRepository diaryPageRepository;
    LessonMapper lessonMapper;
    QuestionSolutionMapper lessonSolutionMapper;
    DiaryPageMapper diaryPageMapper;
    QuestionSolutionMapper questionSolutionMapper;

    @BeforeEach
    void setUp() {
        lessonRepository = mock(LessonRepository.class);
        questionSolutionRepository = mock(QuestionSolutionRepository.class);
        diaryPageRepository = mock(DiaryPageRepository.class);
        lessonMapper = mock(LessonMapper.class);
        lessonSolutionMapper = mock(QuestionSolutionMapper.class);
        diaryPageMapper = mock(DiaryPageMapper.class);
        questionSolutionMapper = mock(QuestionSolutionMapper.class);
        lessonPageService = new LessonPageService(lessonRepository,
                questionSolutionRepository,
                diaryPageRepository,
                lessonMapper,
                lessonSolutionMapper,
                diaryPageMapper,
                questionSolutionMapper);
    }

    @Test
    void happyPath_getLessonDTO() {
        Long sampleId = 1L;
        LessonEntity lessonEntity = new LessonEntity();
        LessonDTO lessonDTO = new LessonDTO();
        stub_getLessonDTO(sampleId, lessonEntity, lessonDTO);

        LessonDTO result = lessonPageService.getLessonDTO(sampleId);
        assertThat(result).isEqualTo(lessonDTO);
    }

    void stub_getLessonDTO(Long id, LessonEntity lessonEntity, LessonDTO lessonDTO) {
        when(lessonRepository.findById(eq(id))).thenReturn(Optional.ofNullable(lessonEntity));
        when(lessonMapper.from(eq(lessonEntity))).thenReturn(lessonDTO);
    }

    @Test
    void nullLessonEntity_getLessonDTO() {
        Long sampleId = 1L;
        stub_getLessonDTO(sampleId, null, null);

        Assertions.assertThrows(EntityNotFoundException.class,
                ()-> lessonPageService.getLessonDTO(sampleId),
                "Lesson id not found - 1");
    }

    @Test
    void happyPath_getLessonSolutionDTO() {
        Long sampleId = 1L;
        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity();
        QuestionSolutionDTO questionSolutionDTO = new QuestionSolutionDTO();
        stub_getLessonSolutionDTO(sampleId, questionSolutionEntity, questionSolutionDTO);

        QuestionSolutionDTO result = lessonPageService.getLessonSolutionDTO(sampleId);
        assertThat(result).isEqualTo(questionSolutionDTO);
    }

    void stub_getLessonSolutionDTO(Long id, QuestionSolutionEntity questionSolutionEntity, QuestionSolutionDTO questionSolutionDTO) {
        when(questionSolutionRepository.findById(id)).thenReturn(Optional.ofNullable(questionSolutionEntity));
        when(lessonSolutionMapper.from(questionSolutionEntity)).thenReturn(questionSolutionDTO);
    }

    @Test
    void happyPath_postNewLesson() {
        String lesson = "lesson";
        String question = "question";
        String answerOptionSolution = "answerOptionSolution";
        List<String> answerOptions = asList(answerOptionSolution, "option2", "option3");
        String solutionDescription = "solutionDescription";

        LessonPostDTO lessonPostDTO = new LessonPostDTO(lesson, question, answerOptions, answerOptionSolution,
                solutionDescription);
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setEntry(lesson);
        lessonEntity.setQuestions(Collections.emptySet());
        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity();
        questionSolutionEntity.setQuestion(question);
        questionSolutionEntity.setOptions(answerOptions);
        questionSolutionEntity.setOptionSolution(answerOptionSolution);
        questionSolutionEntity.setDescription(solutionDescription);
        questionSolutionEntity.setLesson(lessonEntity);

        Set<QuestionSolutionEntity> questionSet = new HashSet<>();
        questionSet.add(questionSolutionEntity);
        lessonEntity.setQuestions(questionSet);
        LessonDTO lessonDTO = new LessonDTO(lessonEntity.getId(),
                lessonEntity.getEntry(),
                Collections.emptySet());
        QuestionSolutionDTO questionSolutionDTO = new QuestionSolutionDTO(questionSolutionEntity.getId(),
                questionSolutionEntity.getQuestion(),
                questionSolutionEntity.getOptions(),
                questionSolutionEntity.getOptionSolution(),
                questionSolutionEntity.getDescription());
        Set<QuestionSolutionDTO> questionSolutionDTOs = new HashSet<>();
        questionSolutionDTOs.add(questionSolutionDTO);
        lessonDTO.setQuestions(questionSolutionDTOs);

        stub_postNewLesson(lessonEntity, questionSolutionEntity, questionSolutionDTO, lessonDTO);

        LessonDTO result = lessonPageService.postNewLesson(lessonPostDTO);
        assertThat(result).isEqualTo(lessonDTO);
        // test of find in database here as well
    }

    void stub_postNewLesson(LessonEntity lessonEntity, QuestionSolutionEntity questionSolutionEntity,
                            QuestionSolutionDTO questionSolutionDTO, LessonDTO lessonDTO) {
        when(lessonRepository.saveAndFlush(any())).thenReturn(lessonEntity);
        when(questionSolutionRepository.saveAndFlush(any())).thenReturn(questionSolutionEntity);
        when(questionSolutionMapper.from(eq(questionSolutionEntity))).thenReturn(questionSolutionDTO);
        when(lessonMapper.from(eq(lessonEntity))).thenReturn(lessonDTO);
    }

    @Test
    void happyPath_updateLesson() {
        LessonEntity lessonEntity = new LessonEntity();
        LessonDTO lessonDTO = new LessonDTO();
        LessonEntity updateLessonEntity = new LessonEntity();
        LessonDTO updateLessonDTO = new LessonDTO();
        sub_updateLesson(lessonEntity, lessonDTO, updateLessonEntity, updateLessonDTO);

        LessonDTO result = lessonPageService.updateLesson(lessonDTO);
        assertThat(result).isEqualTo(updateLessonDTO);
    }

    void sub_updateLesson(LessonEntity lessonEntity, LessonDTO lessonDTO, LessonEntity updateLessonEntity,
                          LessonDTO updateLessonDTO) {
        when(lessonRepository.findById(any())).thenReturn(Optional.ofNullable(lessonEntity));
        when(lessonMapper.to(eq(lessonDTO))).thenReturn(updateLessonEntity);
        when(lessonRepository.saveAndFlush(any())).thenReturn(updateLessonEntity);
        when(lessonMapper.from(eq(updateLessonEntity))).thenReturn(updateLessonDTO);
    }

    @Test
    void happyPath_postNewQuestion() {
        Long id = 1L;
        String question = "question";
        String answerOptionSolution = "answerOptionSolution";
        List<String> answerOptions = asList(answerOptionSolution, "option2", "option3");
        String solutionDescription = "solutionDescription";
        String entry = "entry";

        QuestionPostDTO questionPostDTO = new QuestionPostDTO(id, question,
                answerOptions, answerOptionSolution, solutionDescription);
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setEntry(entry);
        lessonEntity.setQuestions(Collections.emptySet());
        QuestionSolutionDTO questionSolutionDTO = new QuestionSolutionDTO();
        stub_postNewQuestion(id, lessonEntity, questionSolutionDTO);

        QuestionSolutionDTO result = lessonPageService.postNewQuestion(questionPostDTO);
        assertThat(result).isEqualTo(questionSolutionDTO);
    }

    void stub_postNewQuestion(Long lessonId, LessonEntity lessonEntity, QuestionSolutionDTO questionSolutionDTO) {
        when(lessonRepository.findById(eq(lessonId))).thenReturn(Optional.ofNullable(lessonEntity));
        when(questionSolutionMapper.from(any())).thenReturn(questionSolutionDTO);
    }

    @Test
    void evaluateAnswer() {
        // meed database
    }

    @Test
    void happyPath_postNewAnswer() {
        LessonUserAnswerDTO inputLessonUserAnswerDTO = new LessonUserAnswerDTO();
        String commentary = "commentary";
        inputLessonUserAnswerDTO.setAnswerDiaryEntry(commentary);
        inputLessonUserAnswerDTO.setQuestionId(1L);
        inputLessonUserAnswerDTO.setAnswerOption("SameAnswer");

        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity();
        questionSolutionEntity.setOptionSolution("SameAnswer");

        DiaryPageEntity diaryPageEntity = new DiaryPageEntity();
        diaryPageEntity.setWrittenDate(LocalDate.of(2024, 9, 9));
        diaryPageEntity.setEntry("The answer is " + "true" + ". Comments: " + commentary);
        DiaryPageDTO diaryPageDTO = new DiaryPageDTO(diaryPageEntity.getWrittenDate(), diaryPageEntity.getEntry());

        stub_HappyPath_PostNewAnswer(inputLessonUserAnswerDTO, questionSolutionEntity, diaryPageEntity, diaryPageDTO);

        DiaryPageDTO result = lessonPageService.postNewAnswer(inputLessonUserAnswerDTO);
        assertThat(result).isEqualTo(diaryPageDTO);
    }

    private void stub_HappyPath_PostNewAnswer(LessonUserAnswerDTO inputLessonUserAnswerDTO, QuestionSolutionEntity questionSolutionEntity, DiaryPageEntity diaryPageEntity, DiaryPageDTO diaryPageDTO) {
        when(questionSolutionRepository.findById(eq(inputLessonUserAnswerDTO.getQuestionId())))
                .thenReturn(Optional.of(questionSolutionEntity));
        when(diaryPageRepository.saveAndFlush(any())).thenReturn(diaryPageEntity);
        when(diaryPageMapper.from(diaryPageEntity)).thenReturn(diaryPageDTO);
    }

    @Test
    void false_postNewAnswer() {
        LessonUserAnswerDTO inputLessonUserAnswerDTO = new LessonUserAnswerDTO();
        String commentary = "commentary";
        inputLessonUserAnswerDTO.setAnswerDiaryEntry(commentary);
        inputLessonUserAnswerDTO.setQuestionId(1L);
        inputLessonUserAnswerDTO.setAnswerOption("Answer");

        QuestionSolutionEntity questionSolutionEntity = new QuestionSolutionEntity();
        questionSolutionEntity.setOptionSolution("AnotherAnswer");

        DiaryPageEntity diaryPageEntity = new DiaryPageEntity();
        diaryPageEntity.setWrittenDate(LocalDate.of(2024, 9, 9));
        diaryPageEntity.setEntry("The answer is " + "false" + ". Comments: " + commentary);
        DiaryPageDTO diaryPageDTO = new DiaryPageDTO(diaryPageEntity.getWrittenDate(), diaryPageEntity.getEntry());

        stub_HappyPath_PostNewAnswer(inputLessonUserAnswerDTO, questionSolutionEntity, diaryPageEntity, diaryPageDTO);

        DiaryPageDTO result = lessonPageService.postNewAnswer(inputLessonUserAnswerDTO);
        assertThat(result).isEqualTo(diaryPageDTO);
    }
}