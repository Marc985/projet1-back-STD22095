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
        return  patrimonyService.getPatrimony(id);
    }
    @PutMapping("/patrimony/{id}")
    public Patrimony createOrUpdate(@PathVariable String id, @RequestBody Patrimony patrimony) throws IOException {
       return patrimonyService.saveOrUpdatePatrimony(id,patrimony.name());
    }

}
