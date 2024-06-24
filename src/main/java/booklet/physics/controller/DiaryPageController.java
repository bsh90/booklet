package booklet.physics.controller;

import booklet.physics.dto.DiaryPageDTO;
import booklet.physics.service.lesson.DiaryPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DiaryPageController {

    @Autowired
    DiaryPageService diaryPageService;

    @GetMapping
    public DiaryPageDTO getDiaryPage(@PathVariable("id") Long id) {
        return diaryPageService.getDiaryPageDTO(id);
    }
}
