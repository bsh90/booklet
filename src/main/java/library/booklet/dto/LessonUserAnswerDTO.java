package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonUserAnswerDTO {

    private Long questionId;

    private String answerOption;

    private String answerDiaryEntry;
}
