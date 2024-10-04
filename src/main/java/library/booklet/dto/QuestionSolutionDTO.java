package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSolutionDTO {

    private Long id;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String question;

    private List<String> options;

    private String optionSolution;

    private String description;

    private LessonDTO lesson;
}
