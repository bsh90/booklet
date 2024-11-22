package library.booklet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInputDTO {

    @NotEmpty(message = "Email should not be empty")
    @Email
    String email;

    @NotEmpty(message = "Password should not be empty")
    String password;
}
