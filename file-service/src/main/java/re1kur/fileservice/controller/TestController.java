package re1kur.fileservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${spring.application.name}")
    private String serviceName;

    @GetMapping("test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Greetings from %s".formatted(serviceName));
    }
}
