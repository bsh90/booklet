package library.booklet.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class LessonAnswerEntity extends BaseEntity{

    private String answerOption;

    private String answerCommentary;

    // TODO
    private Long lessonPageEntityId;
}
