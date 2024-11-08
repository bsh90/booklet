package library.booklet.controller;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
import library.booklet.dto.LessonUserAnswerDTO;
import library.booklet.dto.QuestionPostDTO;
import library.booklet.dto.QuestionSolutionDTO;
import library.booklet.service.lesson.LessonPageService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public LessonDTO getLesson(@RequestParam("id") Long id) {

        return lessonPageService.getLessonDTO(id);
    }

    @PostMapping("/postNewLesson")
    public LessonDTO postNewLesson(@RequestBody LessonPostDTO lessonPostDTO) {
        return lessonPageService.postNewLesson(lessonPostDTO);
    }

    @DeleteMapping("/deleteLesson")
    public void deleteLesson(@RequestParam Long lessonId) {
        lessonPageService.deleteLesson(lessonId);
    }

    @PutMapping("/updateLesson")
    public LessonDTO updateLesson(@RequestBody LessonDTO lessonDTO) {
        return lessonPageService.updateLesson(lessonDTO);
    }

    @PostMapping("/postNewQuestion")
    public QuestionSolutionDTO postNewQuestion(@RequestBody QuestionPostDTO questionPostDTO) {
        return lessonPageService.postNewQuestion(questionPostDTO);
    }

    @DeleteMapping("/deleteQuestion")
    public void deleteQuestion(@RequestParam Long questionId) {
        lessonPageService.deleteQuestion(questionId);
    }

    @PostMapping("/postNewAnswer")
    public DiaryPageDTO postNewAnswer(@RequestBody LessonUserAnswerDTO userAnswerDTO) {
        boolean result = lessonPageService.evaluateAnswer(userAnswerDTO);
        return lessonPageService.postAnswerCommentary(result, userAnswerDTO);
    }

    @DeleteMapping("/deleteAllLessons")
    public void deleteAllLessons() {
        lessonPageService.deleteAllLessons();
    }

    @GetMapping("/getAllLessons")
    public List<LessonDTO> getAllLessons() {
        return lessonPageService.getAllLessons();
    }
}
