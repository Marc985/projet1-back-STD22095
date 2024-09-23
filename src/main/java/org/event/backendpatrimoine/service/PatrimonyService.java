package org.event.backendpatrimoine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.event.backendpatrimoine.modal.Patrimoiny;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class PatrimonyService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Path basePath = Paths.get("patrimoines");

    public PatrimonyService() throws IOException {
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath);
        }
    }

    public Patrimoiny getPatrimoine(String id) throws IOException {
        Path path = getFilePath(id);
        if (Files.exists(path)) {
            return objectMapper.readValue(path.toFile(), Patrimoiny.class);
        } else {
            throw new RuntimeException("Patrimoine not found");
        }
    }

    public void saveOrUpdatePatrimoine(String id, Patrimoiny patrimoine) throws IOException {
        patrimoine.setDerniereModification(LocalDateTime.now());
        objectMapper.writeValue(getFilePath(id).toFile(), patrimoine);
    }

    private Path getFilePath(String id) {
        return basePath.resolve(id + ".json");
    }
}
