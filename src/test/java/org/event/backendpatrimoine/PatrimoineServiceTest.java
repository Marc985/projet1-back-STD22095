package org.event.backendpatrimoine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.event.backendpatrimoine.modal.Patrimony;
import org.event.backendpatrimoine.service.PatrimonyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class PatrimoineServiceTest {
    @InjectMocks
    PatrimonyService patrimonyService;
    @Mock
    private ObjectMapper objectMapper;

    private Path testPath = Paths.get("src/test/resources/patrimoine");

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        patrimonyService = new PatrimonyService(testPath);
        Files.createDirectories(testPath);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testSaveOrUpdatePatrimony() throws IOException {
        String id = "testId";
        String name = "Test Patrimony";

        Patrimony patrimony = patrimonyService.saveOrUpdatePatrimony(id, name);

        assertNotNull(patrimony);
        assertEquals(name, patrimony.name());
        assertNotNull(patrimony.updateDate());

        assertTrue(Files.exists(testPath.resolve(id + ".json")));
    }

    @Test
    void testGetPatrimony() throws IOException {
        String id = "testId";
        Patrimony patrimony = new Patrimony("Test Patrimony", LocalDateTime.now());
        Path path = testPath.resolve(id + ".json");

        when(Files.exists(path)).thenReturn(true);
        when(objectMapper.readValue(path.toFile(), Patrimony.class)).thenReturn(patrimony);

        Patrimony foundPatrimony = patrimonyService.getPatrimony(id);

        assertNotNull(foundPatrimony);
        assertEquals("Test Patrimony", foundPatrimony.name());
    }
}
