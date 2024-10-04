package library.booklet.controller;

import library.booklet.dto.*;
import library.booklet.service.lesson.LessonPageService;
import library.booklet.service.lesson.PublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class LessonPageController {

    @Autowired
    PublishingService publishingService;

    @Autowired
    LessonPageService lessonPageService;

    @GetMapping("/getLessonPageById")
    public LessonDTO getLessonPageById(@RequestParam("id") Long id) {

        return lessonPageService.getLessonDTO(id);
    }

    @PostMapping("/postNewLesson")
    public QuestionSolutionDTO postNewLesson(@RequestBody LessonPostDTO lessonPostDTO) {
        return publishingService.postNewLesson(lessonPostDTO);
    }

    @PostMapping("/postNewQuestion")
    public QuestionSolutionDTO postNewQuestion(@RequestBody QuestionPostDTO questionPostDTO) {
        return publishingService.postNewQuestion(questionPostDTO);
    }

    @PostMapping("/postNewAnswer")
    public DiaryPageDTO postNewAnswer(@RequestBody LessonUserAnswerDTO userAnswerDTO) {
        boolean result = lessonPageService.evaluateAnswer(userAnswerDTO);
        return lessonPageService.postAnswerCommentary(result, userAnswerDTO);
    }
}
