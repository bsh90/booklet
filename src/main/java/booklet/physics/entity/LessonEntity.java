package booklet.physics.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonEntity extends PageEntity {

    private String lesson;

    private String question;

    private String options;

}
