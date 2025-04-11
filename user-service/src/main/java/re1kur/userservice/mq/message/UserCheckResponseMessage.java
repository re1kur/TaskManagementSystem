package re1kur.userservice.mq.message;

public record UserCheckResponseMessage(String email, Boolean isExists, Boolean isVerified) {
}
