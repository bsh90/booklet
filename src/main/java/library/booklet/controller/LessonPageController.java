package library.booklet.controller;

import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
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
    public void postNewLesson(@RequestBody LessonPostDTO lessonPostDTO) {
        publishingService.postANewLesson(lessonPostDTO);
    }
}
