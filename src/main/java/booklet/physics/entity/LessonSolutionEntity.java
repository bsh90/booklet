package booklet.physics.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonSolutionEntity extends PageEntity {

    private String option;

    private String description;

    private Long lessonPageEntityId;
}
