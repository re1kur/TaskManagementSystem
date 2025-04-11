package re1kur.verificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import re1kur.verificationservice.dto.UserVerificationPayload;
import re1kur.verificationservice.exception.VerificationException;
import re1kur.verificationservice.service.CodeService;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class VerificationController {
    private final CodeService service;

    @PostMapping("verify")
    public void verify(@RequestBody UserVerificationPayload payload) throws VerificationException {
        service.verify(payload);
    }
}
