package org.event.backendpatrimoine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.event.backendpatrimoine.modal.Patrimony;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class PatrimonyService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path basePath = Paths.get("src/main/resources/patrimoine");

    public PatrimonyService() throws IOException {
        objectMapper.registerModule(new JavaTimeModule());
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath);
        }
    }

    public Patrimony getPatrimoine(String id) throws IOException {
        Path path = getFilePath(id);
        if (Files.exists(path)) {
            return objectMapper.readValue(path.toFile(), Patrimony.class);
        } else {
            throw new RuntimeException("Patrimoine not found");
        }
    }

    public void saveOrUpdatePatrimoine(String id, Patrimony patrimoine) throws IOException {
        patrimoine.setUpdateDate(LocalDateTime.now());
        objectMapper.writeValue(getFilePath(id).toFile(), patrimoine);
    }

    private Path getFilePath(String id) {
        return basePath.resolve(id + ".json");
    }
}
