package org.event.backendpatrimoine.controller;

import org.event.backendpatrimoine.modal.Patrimony;
import org.event.backendpatrimoine.service.PatrimonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PatrimonyController {
    @Autowired
    PatrimonyService patrimonyService;
    @GetMapping("/patrimony/{id}")
    public Patrimony getPatrimony(@PathVariable String id) throws IOException {
        return  patrimonyService.getPatrimoine(id);
    }
    @PutMapping("/patrimony/{id}")
    public void createOrUpdate(@PathVariable String id, @RequestBody Patrimony patrimoiny) throws IOException {
        patrimonyService.saveOrUpdatePatrimoine(id, patrimoiny);
    }

}
