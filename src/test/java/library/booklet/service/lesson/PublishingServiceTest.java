//package library.booklet.service.lesson;
//
//import library.booklet.dto.LessonPostDTO;
//import library.booklet.entity.LessonEntity;
//import library.booklet.entity.LessonSolutionEntity;
//import library.booklet.repository.LessonRepository;
//import library.booklet.repository.LessonSolutionRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//class PublishingServiceTest {
//
//    PublishingService publishingService;
//    LessonRepository lessonRepository;
//    LessonSolutionRepository lessonSolutionRepository;
//
//    @BeforeEach
//    void setUp() {
//        lessonRepository = mock(LessonRepository.class);
//        lessonSolutionRepository = mock(LessonSolutionRepository.class);
//        publishingService = new PublishingService(lessonRepository, lessonSolutionRepository);
//    }
//
//    @Test
//    void postANewLesson() {
//        String lesson = "lesson";
//        String question = "question";
//        String answerOption = "answerOption";
//        String answerOptionSolution = "answerOptionSolution";
//        String solutionDescription = "solutionDescription";
//
//        LessonPostDTO lessonPostDTO = new LessonPostDTO(lesson, question, answerOption,
//                answerOptionSolution, solutionDescription);
//        LessonEntity lessonEntity = new LessonEntity(lesson, question, answerOption);
//        LessonSolutionEntity lessonSolutionEntity = new LessonSolutionEntity(answerOptionSolution,
//                solutionDescription, 1L);
//        stub_postANewLesson(lessonEntity, lessonSolutionEntity);
//
//        LessonSolutionEntity result = publishingService.postANewLesson(lessonPostDTO);
//        assertThat(result).isEqualTo(lessonSolutionEntity);
//        // test of find in database here as well
//    }
//
//    void stub_postANewLesson(LessonEntity lessonEntity, LessonSolutionEntity lessonSolutionEntity) {
//        when(lessonRepository.saveAndFlush(any())).thenReturn(lessonEntity);
//        when(lessonSolutionRepository.saveAndFlush(any())).thenReturn(lessonSolutionEntity);
//    }
//}