package library.booklet.controller;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.mapper.DiaryPageMapper;
import library.booklet.service.lesson.DiaryPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DiaryPageController {

    @Autowired
    DiaryPageService diaryPageService;

    @Autowired
    DiaryPageMapper diaryPageMapper;

    public DiaryPageController(DiaryPageService diaryPageService,
                               DiaryPageMapper diaryPageMapper) {
        this.diaryPageService = diaryPageService;
        this.diaryPageMapper = diaryPageMapper;
    }

    @GetMapping("/getDiaryPage")
    public DiaryPageDTO getDiaryPage(@RequestParam("id") Long id) {

        return diaryPageService.getDiaryPageDTO(id);
    }

    @GetMapping("/getAllDiaryPages")
    public List<DiaryPageDTO> getAllDiaryPages() {
        return diaryPageService.findAllDiaryPageDTO();
    }

    @PostMapping("/createDiaryPage")
    public void createDiaryPage(@RequestBody DiaryPageDTO diaryPageDTO) {
        diaryPageService.createDiaryPageDTO(diaryPageDTO);
    }

    @DeleteMapping("/deleteDiaryPages")
    public void deleteDiaryPage(@RequestParam("id") long diaryPageId) {
        diaryPageService.deleteDiaryPage(diaryPageId);
    }
}
