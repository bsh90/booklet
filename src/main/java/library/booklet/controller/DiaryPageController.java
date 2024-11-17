package library.booklet.controller;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.mapper.DiaryPageMapper;
import library.booklet.service.lesson.DiaryPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
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
    public DiaryPageDTO getDiaryPage(@RequestParam("id") Long id) { return diaryPageService.getDiaryPageDTO(id); }

    @GetMapping("/getDiaryOfDate")
    public List<DiaryPageDTO> getDiaryOfDate(@RequestParam("writtenDate") String writtenDateString) {
        return diaryPageService.getDiaryOfDate(writtenDateString);
    }

    @GetMapping("/getAllDiaryPages")
    public List<DiaryPageDTO> getAllDiaryPages() {
        return diaryPageService.findAllDiaryPageDTO();
    }

    @GetMapping("/getAllDiaryPagesWithId")
    public List<DiaryPageEntity> getAllDiaryPagesWithId() {
        return diaryPageService.findAllDiaryPageEntity();
    }

    @PostMapping("/createDiaryPage")
    public void createDiaryPage(@RequestBody DiaryPageDTO diaryPageDTO) {
        diaryPageService.createDiaryPageDTO(diaryPageDTO);
    }

    @DeleteMapping("/deleteDiaryPages")
    public void deleteDiaryPage(@RequestParam("id") long diaryPageId) {
        diaryPageService.deleteDiaryPage(diaryPageId);
    }

    @GetMapping("/sortACSDiaryPageBasedOnWrittenDate")
    public List<DiaryPageEntity> sortACSDiaryPageBasedOnWrittenDate() {
        return diaryPageService.sortACSDiaryPageBasedOnWrittenDate();
    }

    @GetMapping("/requestDiaryPages")
    public List<DiaryPageEntity> requestDiaryPagesSortByWrittenDate(@RequestParam("pageNumber") int pageNumber,
                                                   @RequestParam("pageSize") int pageSize) {
        return diaryPageService.requestDiaryPagesSortByWrittenDate(pageNumber, pageSize);
    }
}
