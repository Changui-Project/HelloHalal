package cu.dev.halal.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public String upload(byte[] multipartFile) throws IOException;
    public byte[] download(Long id);

}
