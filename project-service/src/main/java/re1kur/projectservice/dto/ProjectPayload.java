package re1kur.projectservice.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectPayload(
        @NotBlank(message = "Project title must be not empty.") String title,
        String description
) {
}
