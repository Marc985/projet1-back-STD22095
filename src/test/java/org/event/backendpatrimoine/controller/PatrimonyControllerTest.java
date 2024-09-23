package org.event.backendpatrimoine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.event.backendpatrimoine.controller.PatrimonyController;
import org.event.backendpatrimoine.exception.PatrimonyNotFound;
import org.event.backendpatrimoine.modal.Patrimony;
import org.event.backendpatrimoine.service.PatrimonyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;


import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatrimonyController.class)
public class PatrimonyControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private PatrimonyService patrimonyService;

    @Autowired
    private ObjectMapper objectMapper;

    private Patrimony samplePatrimony;

    @BeforeEach
    void setUp() {
        samplePatrimony = new Patrimony("Sample Patrimony", LocalDateTime.now());
    }

    @Test
    void testGetPatrimony_Success() throws Exception {
        String id = "1";


    Mockito.when(patrimonyService.getPatrimony(eq(id))).thenReturn(samplePatrimony);

        mockMvc.perform(get("/patrimony/{id}", id))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) content().json(objectMapper.writeValueAsString(samplePatrimony)));
    }

    @Test
    void testCreateOrUpdatePatrimony_Success() throws Exception {
        String id = "1";
        Patrimony patrimonyToSave = new Patrimony("Updated Patrimony", LocalDateTime.now());

        when(patrimonyService.saveOrUpdatePatrimony(eq(id), any(String.class))).thenReturn(patrimonyToSave);

        mockMvc.perform(put("/patrimony/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patrimonyToSave)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(patrimonyToSave)));
    }




}
