package booklet.physics.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonAnswerEntity extends BaseEntity{

    private String answerOption;

    private String answerCommentary;

    // TODO
    private Long lessonPageEntityId;
}
