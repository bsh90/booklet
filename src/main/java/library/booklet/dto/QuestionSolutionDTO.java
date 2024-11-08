package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSolutionDTO {

    private Long id;

    private String question;

    private List<String> options;

    private String optionSolution;

    private String description;
}
