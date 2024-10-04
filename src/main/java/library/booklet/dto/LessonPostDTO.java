package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonPostDTO {

    private String lesson;

    private String question;

    private String answerOption;

    private String answerOptionSolution;

    private String solutionDescription;
}
