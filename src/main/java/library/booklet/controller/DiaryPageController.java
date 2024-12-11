package library.booklet.controller;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.mapper.DiaryPageMapper;
import library.booklet.service.lesson.DiaryPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getDiaryPage(@RequestParam("id") Long id) {
        return diaryPageService.getDiaryPageDTO(id);

    }

    @GetMapping("/getDiaryOfDate")
    public ResponseEntity<List<DiaryPageDTO>> getDiaryOfDate(@RequestParam("writtenDate") String writtenDateString) {
        List<DiaryPageDTO> diaryPageDTOList = diaryPageService.getDiaryOfDate(writtenDateString);
        return ResponseEntity.ok(diaryPageDTOList);
    }

    @GetMapping("/getAllDiaryPages")
    public ResponseEntity<List<DiaryPageDTO>> getAllDiaryPages() {
        List<DiaryPageDTO> diaryPageDTOList = diaryPageService.findAllDiaryPageDTO();
        return ResponseEntity.ok(diaryPageDTOList);
    }

    @GetMapping("/getAllDiaryPagesWithId")
    public ResponseEntity<List<DiaryPageEntity>> getAllDiaryPagesWithId() {
        List<DiaryPageEntity> diaryPageEntities = diaryPageService.findAllDiaryPageEntity();
        return ResponseEntity.ok(diaryPageEntities);
    }

    @PostMapping("/createDiaryPage")
    public ResponseEntity<DiaryPageEntity> createDiaryPage(@RequestBody DiaryPageDTO diaryPageDTO) {
        DiaryPageEntity diaryPageEntity = diaryPageService.createDiaryPageDTO(diaryPageDTO);
        return ResponseEntity.ok(diaryPageEntity);
    }

    @DeleteMapping("/deleteDiaryPages")
    public ResponseEntity<?> deleteDiaryPage(@RequestParam("id") long diaryPageId) {
        return diaryPageService.deleteDiaryPage(diaryPageId);
    }

    @GetMapping("/sortACSDiaryPageBasedOnWrittenDate")
    public ResponseEntity<List<DiaryPageEntity>> sortACSDiaryPageBasedOnWrittenDate() {
        List<DiaryPageEntity> diaryPageEntities = diaryPageService.sortACSDiaryPageBasedOnWrittenDate();
        return ResponseEntity.ok(diaryPageEntities);
    }

    @GetMapping("/requestDiaryPages")
    public ResponseEntity<List<DiaryPageEntity>> requestDiaryPagesSortByWrittenDate(@RequestParam("pageNumber") int pageNumber,
                                                   @RequestParam("pageSize") int pageSize) {
        List<DiaryPageEntity> diaryPageEntities = diaryPageService.requestDiaryPagesSortByWrittenDate(pageNumber, pageSize);
        return ResponseEntity.ok(diaryPageEntities);
    }
}
