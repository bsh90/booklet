package library.booklet.controller;

import library.booklet.dto.DiaryPageDTO;
import library.booklet.entity.DiaryPageEntity;
import library.booklet.mapper.DiaryPageMapper;
import library.booklet.repository.DiaryPageRepository;
import library.booklet.service.lesson.DiaryPageService;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiaryPageController {

    @Autowired
    DiaryPageService diaryPageService;

    @Autowired
    DiaryPageRepository diaryPageRepository;

    @Autowired
    DiaryPageMapper diaryPageMapper;

    public DiaryPageController(DiaryPageService diaryPageService,
                               DiaryPageRepository diaryPageRepository,
                               DiaryPageMapper diaryPageMapper) {
        this.diaryPageService = diaryPageService;
        this.diaryPageRepository = diaryPageRepository;
        this.diaryPageMapper = diaryPageMapper;
    }

    @GetMapping
    public DiaryPageDTO getDiaryPage(@PathVariable("id") Long id) {
        return diaryPageService.getDiaryPageDTO(id);
    }

    @PostMapping
    public void createDiaryPage(@RequestBody DiaryPageDTO diaryPageDTO) {
        diaryPageService.createDiaryPageDTO(diaryPageDTO);
    }

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }

    @GetMapping("/diaryPages")
    public List<DiaryPageDTO> findAllDiaryPages() {
        return diaryPageRepository.findAll()
                .stream()
                .map(e -> diaryPageMapper.from(e))
                .toList();
    }
//
//    @GetMapping("/diaryPages/{diaryPageId}")
//    public DiaryPageDTO getDiaryPage(@PathVariable long diaryPageId) {
//        DiaryPageEntity diaryPage = diaryPageRepository.findById(diaryPageId)
//                .orElseThrow(() -> new RuntimeException("DiaryPage id not found - " + diaryPageId));
//        return diaryPageMapper.from(diaryPage);
//    }

//    @PostMapping("/diaryPages")
//    public DiaryPageDTO addDiaryPage(@RequestBody DiaryPageDTO diaryPage) {
//
//        DiaryPageEntity newDiaryPage = diaryPageRepository.save(diaryPage);
//        return diaryPageMapper.from(newDiaryPage);
//    }
//
//    @PutMapping("/diaryPages")
//    public DiaryPageDTO updateDiaryPage(@RequestBody DiaryPageDTO diaryPage) {
//        DiaryPageEntity updatedDiaryPage = diaryPageRepository.save(diaryPage);
//        return diaryPageMapper.from(updatedDiaryPage);
//    }


//    @DeleteMapping("/diaryPages/{diaryPageId}")
//    public String deleteDiaryPage(@PathVariable long diaryPageId) {
//        DiaryPageEntity diaryPage = diaryPageRepository.findById(diaryPageId)
//                .orElseThrow(() -> new RuntimeException("DiaryPage id not found - " + diaryPageId));
//        diaryPageRepository.delete(diaryPage);
//        return "Deleted diaryPage with id: " + diaryPageId;
//    }
}
