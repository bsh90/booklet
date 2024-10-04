package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {

    private Long id;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String entry;

    private Set<QuestionSolutionDTO> questions;
}
