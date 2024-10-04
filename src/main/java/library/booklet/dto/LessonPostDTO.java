package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonPostDTO {

    private String lesson;

    private String initialQuestion;

    private List<String> answerOptionOfInitialQuestion;

    private String answerOptionSolutionOfInitialQuestion;

    private String solutionDescriptionOfInitialQuestion;
}
