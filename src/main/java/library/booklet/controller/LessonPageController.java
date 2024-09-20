package library.booklet.controller;

import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
import library.booklet.service.lesson.LessonPageService;
import library.booklet.service.lesson.PublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LessonPageController {

    @Autowired
    PublishingService publishingService;

    @Autowired
    LessonPageService lessonPageService;

    @PostMapping
    public void postNewLesson(@RequestBody LessonPostDTO lessonPostDTO) {
        publishingService.postANewLesson(lessonPostDTO);
    }

    @GetMapping
    public LessonDTO getLessonPageById(@PathVariable("id") Long id) {

        return lessonPageService.getLessonDTO(id);
    }
}
