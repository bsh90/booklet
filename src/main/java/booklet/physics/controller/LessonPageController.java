package booklet.physics.controller;

import booklet.physics.dto.LessonDTO;
import booklet.physics.dto.LessonPostDTO;
import booklet.physics.service.lesson.LessonPageService;
import booklet.physics.service.lesson.PublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
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
