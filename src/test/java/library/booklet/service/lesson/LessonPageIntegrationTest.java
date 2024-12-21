package library.booklet.service.lesson;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
import library.booklet.dto.LessonUserAnswerDTO;
import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.dto.QuestionPostDTO;
import library.booklet.entity.LessonEntity;
import library.booklet.entity.QuestionSolutionEntity;
import library.booklet.repository.LessonRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LessonPageIntegrationTest {

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    LessonEntity lesson;

    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

    @BeforeAll
    static void beforeAll() {
        mysql.start();
    }

    @AfterAll
    static void afterAll() {
        mysql.stop();
    }

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeEach
    void setUp() {
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setEntry("entry");
        QuestionSolutionEntity questionSolution = new QuestionSolutionEntity();
        questionSolution.setOptionSolution("b");
        questionSolution.setLesson(lessonEntity);
        questionSolution.setQuestion("question?");
        questionSolution.setOptions(List.of("a.1", "b.2", "c.3", "d.4"));
        questionSolution.setDescription("description");
        Set<QuestionSolutionEntity> questionSolutionEntities = Set.of(questionSolution);
        lessonEntity.setQuestions(questionSolutionEntities);
        lesson = lessonRepository.saveAndFlush(lessonEntity);
    }

    @AfterEach
    public void afterEach(){
        lessonRepository.deleteAll();
    }

    @Test
    void happyPath_getLesson() {
        ResponseEntity<LessonDTO> response = testRestTemplate.exchange("/getLessonPageById"+"?id="+lesson.getId(),
                HttpMethod.GET, getHttpEntity(), LessonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEntry()).isEqualTo(lesson.getEntry());
    }

    private static HttpEntity<String> getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        return new HttpEntity<>(null, httpHeaders);
    }

    @Test
    void notFound_getLesson() {
        ResponseEntity<String> response = testRestTemplate.exchange("/getLessonPageById"+"?id=57577",
                HttpMethod.GET, getHttpEntity(), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Lesson id not found - 57577");
    }

    @Test
    void happyPath_postNewLesson() {
        LessonPostDTO lessonPostDTO = new LessonPostDTO();
        String lessonText = "lesson";
        lessonPostDTO.setLesson(lessonText);
        String initialQuestion = "initial question?";
        lessonPostDTO.setInitialQuestion(initialQuestion);
        List<String> answerOption = List.of("a.2", "b.3", "c.4", "d.5");
        lessonPostDTO.setAnswerOptionOfInitialQuestion(answerOption);
        String answerOptionSolution = "c";
        lessonPostDTO.setAnswerOptionSolutionOfInitialQuestion(answerOptionSolution);
        String solutionDescription = "solution description";
        lessonPostDTO.setSolutionDescriptionOfInitialQuestion(solutionDescription);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LessonPostDTO> httpEntity = new HttpEntity<>(lessonPostDTO, httpHeaders);
        ResponseEntity<LessonDTO> response = testRestTemplate.exchange("/postNewLesson",
                HttpMethod.POST, httpEntity, LessonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEntry()).isEqualTo(lessonText);
        QuestionSolutionDTO question = response.getBody().getQuestions().iterator().next();
        assertThat(question.getQuestion()).isEqualTo(initialQuestion);
        assertThat(question.getOptions()).isEqualTo(answerOption);
        assertThat(question.getOptionSolution()).isEqualTo(answerOptionSolution);
        assertThat(question.getDescription()).isEqualTo(solutionDescription);
    }

    @Test
    void happyPath_deleteLesson() {
        ResponseEntity<?> response = testRestTemplate.exchange("/deleteLesson"+"?id="+lesson.getId(),
                HttpMethod.DELETE, getHttpEntity(), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void happyPath_updateLesson() {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(lesson.getId());
        String lessonText = "lesson";
        lessonDTO.setEntry(lessonText);
        String initialQuestion = "initial question?";
        QuestionSolutionDTO questionSolutionDTO = new QuestionSolutionDTO();
        questionSolutionDTO.setQuestion(initialQuestion);
        List<String> answerOption = List.of("a.2", "b.3", "c.4", "d.5");
        questionSolutionDTO.setOptions(answerOption);
        String answerOptionSolution = "c";
        questionSolutionDTO.setOptionSolution(answerOptionSolution);
        String solutionDescription = "solution description";
        questionSolutionDTO.setDescription(solutionDescription);
        lessonDTO.setQuestions(Set.of(questionSolutionDTO));
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LessonDTO> httpEntity = new HttpEntity<>(lessonDTO, httpHeaders);
        ResponseEntity<LessonDTO> response = testRestTemplate.exchange("/updateLesson",
                HttpMethod.PUT, httpEntity, LessonDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEntry()).isEqualTo(lessonText);
        QuestionSolutionDTO question = response.getBody().getQuestions().iterator().next();
        assertThat(question.getQuestion()).isEqualTo(initialQuestion);
        assertThat(question.getOptions()).isEqualTo(answerOption);
        assertThat(question.getOptionSolution()).isEqualTo(answerOptionSolution);
        assertThat(question.getDescription()).isEqualTo(solutionDescription);
    }

    @Test
    void noId_updateLesson() {
        LessonDTO lessonDTO = new LessonDTO();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LessonDTO> httpEntity = new HttpEntity<>(lessonDTO, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange("/updateLesson",
                HttpMethod.PUT, httpEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("LessonDTO doesn't have Id.");
    }

    @Test
    void notFound_updateLesson() {
        LessonDTO lessonDTO = new LessonDTO();
        lessonDTO.setId(98757L);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LessonDTO> httpEntity = new HttpEntity<>(lessonDTO, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange("/updateLesson",
                HttpMethod.PUT, httpEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Lesson not found.");
    }

    @Test
    void happyPath_postNewQuestion() {
        QuestionPostDTO questionPostDTO = new QuestionPostDTO();
        questionPostDTO.setLessonId(lesson.getId());
        String questionText = "question??";
        questionPostDTO.setQuestion(questionText);
        List<String> answerOption = List.of("a.2", "b.3", "c.4", "d.5");
        questionPostDTO.setAnswerOption(answerOption);
        String optionSolution = "c";
        questionPostDTO.setAnswerOptionSolution(optionSolution);
        String description = "description";
        questionPostDTO.setSolutionDescription(description);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<QuestionPostDTO> httpEntity = new HttpEntity<>(questionPostDTO, httpHeaders);
        ResponseEntity<QuestionSolutionDTO> response = testRestTemplate.exchange("/postNewQuestion",
                HttpMethod.POST, httpEntity, QuestionSolutionDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        QuestionSolutionDTO question = response.getBody();
        assertThat(question).isNotNull();
        assertThat(question.getQuestion()).isEqualTo(questionText);
        assertThat(question.getOptions()).isEqualTo(answerOption);
        assertThat(question.getOptionSolution()).isEqualTo(optionSolution);
        assertThat(question.getDescription()).isEqualTo(description);
    }

    @Test
    void happyPath_deleteQuestion() {
        ResponseEntity<?> response = testRestTemplate.exchange("/deleteQuestion"+
                        "?id="+lesson.getQuestions().iterator().next().getId(),
                HttpMethod.DELETE, getHttpEntity(), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void happyPath_postNewAnswer() {
        LessonUserAnswerDTO userAnswerDTO = new LessonUserAnswerDTO();
        userAnswerDTO.setQuestionId(lesson.getQuestions().iterator().next().getId());
        userAnswerDTO.setAnswerOption("b");
        String diary = "diary entry";
        userAnswerDTO.setAnswerDiaryEntry(diary);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LessonUserAnswerDTO> httpEntity = new HttpEntity<>(userAnswerDTO, httpHeaders);
        ResponseEntity<DiaryPageDTO> response = testRestTemplate.exchange("/postNewAnswer",
                HttpMethod.POST, httpEntity, DiaryPageDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEntry()).isEqualTo("The answer is true. Your Comments: "+diary);
        assertThat(response.getBody().getWrittenDate()).isEqualTo(LocalDate.now().toString());
    }

    @Test
    void falseAnswer_postNewAnswer() {
        LessonUserAnswerDTO userAnswerDTO = new LessonUserAnswerDTO();
        userAnswerDTO.setQuestionId(lesson.getQuestions().iterator().next().getId());
        userAnswerDTO.setAnswerOption("d");
        String diary = "diary entry";
        userAnswerDTO.setAnswerDiaryEntry(diary);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LessonUserAnswerDTO> httpEntity = new HttpEntity<>(userAnswerDTO, httpHeaders);
        ResponseEntity<DiaryPageDTO> response = testRestTemplate.exchange("/postNewAnswer",
                HttpMethod.POST, httpEntity, DiaryPageDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEntry()).isEqualTo("The answer is false. Your Comments: "+diary);
        assertThat(response.getBody().getWrittenDate()).isEqualTo(LocalDate.now().toString());
    }

    @Test
    void notFound_postNewAnswer() {
        LessonUserAnswerDTO userAnswerDTO = new LessonUserAnswerDTO();
        userAnswerDTO.setQuestionId(1231313L);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<LessonUserAnswerDTO> httpEntity = new HttpEntity<>(userAnswerDTO, httpHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange("/postNewAnswer",
                HttpMethod.POST, httpEntity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Question entity not found");
    }

    @Test
    void happyPath_getAllLessons() {
        ResponseEntity<List<LessonDTO>> response = testRestTemplate.exchange("/getAllLessons",
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<LessonDTO>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getEntry()).isEqualTo(lesson.getEntry());
    }
}
