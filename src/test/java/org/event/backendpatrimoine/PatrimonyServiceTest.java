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
@Autowired
PatrimonyService patrimonyService;
@Test
void createPatrimone() throws IOException {
    Patrimony patrimonyToCreate=new Patrimony("test",LocalDateTime.now());
   Patrimony response= patrimonyService.saveOrUpdatePatrimony("4",patrimonyToCreate.name());
 assertEquals(patrimonyToCreate,response);
}
@Test
    void getPatrimoine() throws IOException {
    assertEquals(patrimonyService.getPatrimony("4").name(),"test");
}


}
