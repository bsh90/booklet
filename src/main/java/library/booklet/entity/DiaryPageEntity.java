package library.booklet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class DiaryPageEntity extends BaseEntity{

    @Column(name = "WrittenDate")
    private LocalDate writtenDate;

    @Column(name = "Entry", columnDefinition="VARCHAR(MAX)")
    private String entry;
}
