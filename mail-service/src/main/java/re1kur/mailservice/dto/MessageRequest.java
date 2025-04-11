package re1kur.mailservice.dto;


public record MessageRequest (String email, String subject, String content) {
}
