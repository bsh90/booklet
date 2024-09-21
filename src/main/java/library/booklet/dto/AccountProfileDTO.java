package library.booklet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountProfileDTO {

    private Long id;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private String name;

    private String info;
}
