package org.event.backendpatrimoine;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.event.backendpatrimoine.exception.InvalidPatrimoine;
import org.event.backendpatrimoine.exception.PatrimonyNotFound;
import org.event.backendpatrimoine.modal.Patrimony;
import org.event.backendpatrimoine.service.PatrimonyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
public class PatrimonyServiceTest {
    @MockBean
    private ObjectMapper objectMapper;

    @Autowired
    private PatrimonyService patrimonyService;

    @BeforeEach
    void setUp() throws IOException {
        when(objectMapper.writeValueAsString(any())).thenAnswer(invocation -> {
            Patrimony patrimony = invocation.getArgument(0);
            return "{ \"name\": \"" + patrimony.name() + "\", \"updateDate\": \"" + patrimony.updateDate() + "\" }"; // Simulez la sérialisation
        });

        when(objectMapper.readValue((JsonParser) any(), eq(Patrimony.class))).thenAnswer(invocation -> {
            String json = invocation.getArgument(0);
            return new Patrimony("test", LocalDateTime.now());
        });
    }

    @Test
    void createPatrimoine() throws IOException {
        Patrimony patrimonyToCreate = new Patrimony("test", LocalDateTime.now());
        Patrimony response = patrimonyService.saveOrUpdatePatrimony("4", patrimonyToCreate.name());

        assertEquals(patrimonyToCreate.name(), response.name());
    }

    @Test
    void getPatrimoine() throws IOException {
        Patrimony patrimony = patrimonyService.getPatrimony("4");
        assertEquals("test", patrimony.name());
    }


}
