package library.booklet.controller;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
import library.booklet.dto.LessonUserAnswerDTO;
import library.booklet.dto.QuestionPostDTO;
import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.service.lesson.LessonPageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    LessonPageService lessonPageService;

    @GetMapping("/getLessonPageById")
    public ResponseEntity<LessonDTO> getLesson(@RequestParam("id") Long id) {
        LessonDTO lessonDTO = lessonPageService.getLessonDTO(id);
        return ResponseEntity.ok(lessonDTO);
    }

    @PostMapping("/postNewLesson")
    public ResponseEntity<LessonDTO> postNewLesson(@RequestBody LessonPostDTO lessonPostDTO) {
        LessonDTO lessonDTO = lessonPageService.postNewLesson(lessonPostDTO);
        return ResponseEntity.ok(lessonDTO);
    }

    @DeleteMapping("/deleteLesson")
    public ResponseEntity deleteLesson(@RequestParam Long lessonId) {
        lessonPageService.deleteLesson(lessonId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateLesson")
    public ResponseEntity<LessonDTO> updateLesson(@RequestBody LessonDTO lessonDTO) {
        LessonDTO lesson = lessonPageService.updateLesson(lessonDTO);
        return ResponseEntity.ok(lesson);
    }

    @PostMapping("/postNewQuestion")
    public ResponseEntity<QuestionSolutionDTO> postNewQuestion(@RequestBody QuestionPostDTO questionPostDTO) {
        QuestionSolutionDTO questionSolutionDTO = lessonPageService.postNewQuestion(questionPostDTO);
        return ResponseEntity.ok(questionSolutionDTO);
    }

    @DeleteMapping("/deleteQuestion")
    public ResponseEntity deleteQuestion(@RequestParam Long questionId) {
        lessonPageService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/postNewAnswer")
    public ResponseEntity<DiaryPageDTO> postNewAnswer(@RequestBody LessonUserAnswerDTO userAnswerDTO) {
        DiaryPageDTO diaryPageDTO = lessonPageService.postNewAnswer(userAnswerDTO);
        return ResponseEntity.ok(diaryPageDTO);
    }

    @GetMapping("/getAllLessons")
    public ResponseEntity<List<LessonDTO>> getAllLessons() {
        List<LessonDTO> lessonDTOS = lessonPageService.getAllLessons();
        return ResponseEntity.ok(lessonDTOS);
    }
}
