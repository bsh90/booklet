package library.booklet.controller;

import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
import library.booklet.dto.LessonUserAnswerDTO;
import library.booklet.dto.QuestionPostDTO;
import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.service.lesson.LessonPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class LessonPageController {

    LessonPageService lessonPageService;

    public LessonPageController(LessonPageService lessonPageService) {
        this.lessonPageService = lessonPageService;
    }

    @GetMapping("/getLessonPageById")
    public ResponseEntity<?> getLesson(@RequestParam("id") Long id) {
        return lessonPageService.getLessonDTO(id);
    }

    @PostMapping("/postNewLesson")
    public ResponseEntity<LessonDTO> postNewLesson(@RequestBody LessonPostDTO lessonPostDTO) {
        LessonDTO lessonDTO = lessonPageService.postNewLesson(lessonPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonDTO);
    }

    @DeleteMapping("/deleteLesson")
    public ResponseEntity<Void> deleteLesson(@RequestParam("id") Long lessonId) {
        lessonPageService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updateLesson")
    public ResponseEntity<?> updateLesson(@RequestBody LessonDTO lessonDTO) {
        return lessonPageService.updateLesson(lessonDTO);
    }

    @PostMapping("/postNewQuestion")
    public ResponseEntity<QuestionSolutionDTO> postNewQuestion(@RequestBody QuestionPostDTO questionPostDTO) {
        QuestionSolutionDTO questionSolutionDTO = lessonPageService.postNewQuestion(questionPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(questionSolutionDTO);
    }

    @DeleteMapping("/deleteQuestion")
    public ResponseEntity<Void> deleteQuestion(@RequestParam("id") Long questionId) {
        lessonPageService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/postNewAnswer")
    public ResponseEntity<?> postNewAnswer(@RequestBody LessonUserAnswerDTO userAnswerDTO) {
        return lessonPageService.postNewAnswer(userAnswerDTO);
    }

    @GetMapping("/getAllLessons")
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        List<LessonDTO> lessonDTOS = lessonPageService.getAllLessons();
        return ResponseEntity.ok(lessonDTOS);
    }
}
