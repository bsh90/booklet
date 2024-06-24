package booklet.physics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonSolutionDTO {

    private String option;

    private String description;

    private Long lessonPageEntityId;
}
