package com.github.siwonpawel.awir.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService
{

    private Path uploadFolder;

    @PostConstruct
    void setup() throws Exception
    {
        uploadFolder = Files.createTempDirectory("awir-uploads");
        log.info("Created temporary folder {}", uploadFolder);
    }

    public Path getUploadFolder()
    {
        return uploadFolder;
    }

    public void store(MultipartFile file)
    {
        if (file == null || file.isEmpty())
        {
            return;
        }

        try
        {
            Path destinationPath = uploadFolder.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            log.info("Saved file [{}]", destinationPath.toAbsolutePath());
        }
        catch (Exception e)
        {
            throw new StorageFileNotFoundException(e);
        }
    }

    public void deleteFileSilently(String filePath)
    {
        try
        {
            Files.delete(Paths.get(filePath));
        }
        catch (Exception exception)
        {
            log.error("Error deleting file {}", filePath, exception);
        }
    }
}
