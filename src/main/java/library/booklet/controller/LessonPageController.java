package library.booklet.controller;

import library.booklet.dto.LessonDTO;
import library.booklet.dto.LessonPostDTO;
import library.booklet.service.lesson.DiaryPageService;
import library.booklet.service.lesson.LessonPageService;
import library.booklet.service.lesson.PublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class LessonPageController {

//    @Autowired
//    PublishingService publishingService;
//
//    @Autowired
//    LessonPageService lessonPageService;
//
//    @PostMapping
//    public void postNewLesson(@RequestBody LessonPostDTO lessonPostDTO) {
//        publishingService.postANewLesson(lessonPostDTO);
//    }
//
//    @GetMapping
//    public LessonDTO getLessonPageById(@PathVariable("id") Long id) {
//
//        return lessonPageService.getLessonDTO(id);
//    }
}
