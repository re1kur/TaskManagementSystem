package re1kur.userservice.dto;

public record UserPayload(String email, String password, Boolean oauth) {
}
