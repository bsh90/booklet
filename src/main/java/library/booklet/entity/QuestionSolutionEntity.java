package library.booklet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "question_solution")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSolutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "Question")
    private String question;

    @Column(name = "Options")
    private List<String> options;

    @Column(name = "OptionSolution")
    private String optionSolution;

    @Column(name = "Description")
    private String description;

    @ManyToOne
    @JoinColumn(name="lesson_id")
    private LessonEntity lesson;
}
