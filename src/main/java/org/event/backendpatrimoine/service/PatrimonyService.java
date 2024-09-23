package org.event.backendpatrimoine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.event.backendpatrimoine.exception.InvalidPatrimoine;
import org.event.backendpatrimoine.exception.PatrimonyNotFound;
import org.event.backendpatrimoine.modal.Patrimony;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public  class PatrimonyService {
    private final ObjectMapper objectMapper ;
    private final Path basePath ;


    public PatrimonyService() throws IOException {
        this.objectMapper= new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.basePath=Paths.get("src/main/resources/patrimoine");
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath);
        }
    }





    public Patrimony getPatrimony(String id) throws IOException {
        Path path = getFilePath(id);
        if (Files.exists(path)) {
            return objectMapper.readValue(path.toFile(), Patrimony.class);
        } else {
            throw new PatrimonyNotFound("Patrimoine with id+"+id+" not found");
        }
    }

    public Patrimony saveOrUpdatePatrimony(String id, String name) throws IOException {

        if (name == null || name.isBlank()) {
            throw new InvalidPatrimoine("Invalid patrimony name");
        }

        LocalDateTime current_date=LocalDateTime.now();
        Patrimony patrimonyToCreate=new Patrimony(name,current_date);
        objectMapper.writeValue(getFilePath(id).toFile(), patrimonyToCreate);
        return  patrimonyToCreate;
    }

    private Path getFilePath(String id) {
        return basePath.resolve(id + ".json");
    }


}
