package re1kur.fileservice.dto;

import java.time.Instant;

public record PresignedUrl(String url, Instant expiration) {
}
