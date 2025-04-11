package re1kur.verificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("verification_code")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Code implements Serializable {
    @Id
    private String email;
    private String content;
    private LocalDateTime expirationDate;
}
