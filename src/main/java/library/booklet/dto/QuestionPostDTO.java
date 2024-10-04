package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPostDTO {

    private Long lessonId;

    private String question;

    private List<String> answerOption;

    private String answerOptionSolution;

    private String solutionDescription;
}
