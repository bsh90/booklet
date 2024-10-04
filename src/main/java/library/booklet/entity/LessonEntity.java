package library.booklet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class LessonEntity extends BaseEntity {

    private String entry;

    @OneToMany(mappedBy = "lesson")
    private Set<QuestionSolutionEntity> questions;

}
