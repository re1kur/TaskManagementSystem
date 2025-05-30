package re1kur.fileservice.client;

import org.springframework.web.multipart.MultipartFile;
import re1kur.fileservice.dto.PresignedUrl;

import java.io.IOException;

public interface StoreClient {
    void upload(String id, MultipartFile payload) throws IOException;

    PresignedUrl getUrl(String id);
}
