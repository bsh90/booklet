package booklet.physics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryPageEntity {

    private LocalDate writtenDate;

    @Column(name = "Entry", columnDefinition="VARCHAR(MAX)")
    private String entry;
}
