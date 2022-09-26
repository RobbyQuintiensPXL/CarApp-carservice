package be.carshop.carservice.service;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Objects;

@Service
public class FileStorageService {

    @Value("${upload-path}")
    private String uploadPath;


    public void save(MultipartFile file) throws FileUploadException {
        Path root = Paths.get(uploadPath);
        try {
            Path resolve = root.resolve(Objects.requireNonNull( LocalDate.now() + "-" + file.getOriginalFilename()));
            if (resolve.toFile()
                    .exists()) {
                Files.deleteIfExists(resolve);
            }
            Files.copy(file.getInputStream(), resolve);
        } catch (Exception e) {
            throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Resource load(String filename) {
        Path root = Paths.get(uploadPath);
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
