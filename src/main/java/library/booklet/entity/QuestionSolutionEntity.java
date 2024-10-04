package library.booklet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSolutionEntity extends BaseEntity {

    private String question;

    private String option;

    private String description;

    @ManyToOne
    @JoinColumn(name="lesson_id", nullable = false)
    private LessonEntity lesson;
}
