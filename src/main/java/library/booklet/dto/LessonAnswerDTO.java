package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonAnswerDTO {
    private String answerOption;

    private String answerCommentary;

    private Long lessonPageEntityId;
}
