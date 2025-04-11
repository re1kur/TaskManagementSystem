package re1kur.verificationservice.mq.message;

public record UserCheckResponseMessage (String email, Boolean isExists, Boolean isVerified) {
}
