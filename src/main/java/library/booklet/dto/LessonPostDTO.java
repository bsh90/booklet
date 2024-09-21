package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonPostDTO {

    private Long id;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String lesson;

    private String question;

    private String answerOption;

    private String answerOptionSolution;

    private String solutionDescription;
}
