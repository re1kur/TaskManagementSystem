package re1kur.fileservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import re1kur.fileservice.service.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class FileController {
    private final FileService service;

    @PostMapping("upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile payload) throws IOException {
        return service.upload(payload);
    }

    @GetMapping("{id}/url")
    private ResponseEntity<String> getUrl(@PathVariable String id) throws FileNotFoundException {
        return service.getUrl(id);
    }
}
