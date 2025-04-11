package re1kur.userservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserPayload(
        @Email(message = "Email has incorrect form.") String email,
        @Size(min = 6, max = 256, message="Password must be between 6 and 256 characters long.") String password,
        Boolean oauth) {
}
