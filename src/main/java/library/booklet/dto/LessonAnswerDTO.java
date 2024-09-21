package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonAnswerDTO {

    private Long id;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String answerOption;

    private String answerCommentary;

    private Long lessonPageEntityId;
}
