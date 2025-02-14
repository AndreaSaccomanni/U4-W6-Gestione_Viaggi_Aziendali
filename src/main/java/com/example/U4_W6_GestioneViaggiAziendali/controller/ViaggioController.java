package com.example.U4_W6_GestioneViaggiAziendali.controller;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Viaggio;
import com.example.U4_W6_GestioneViaggiAziendali.exception.ViaggioNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.payload.ViaggioPayload;
import com.example.U4_W6_GestioneViaggiAziendali.repository.ViaggioDAORepository;
import com.example.U4_W6_GestioneViaggiAziendali.service.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {
    @Autowired
    private ViaggioService viaggioService;

    @PostMapping
    public ResponseEntity<Viaggio> createViaggio(@RequestBody @Validated ViaggioPayload viaggioPayload) {
        Viaggio viaggio = viaggioService.createViaggio(viaggioPayload);
        return new ResponseEntity<>(viaggio, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Viaggio> getViaggio(@PathVariable Long id) {
        try {
            Viaggio viaggio = viaggioService.getViaggio(id);
            return ResponseEntity.ok(viaggio);
        } catch (ViaggioNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<Viaggio> getAllViaggi() {
        return viaggioService.getAllViaggi();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Viaggio> updateViaggio(@PathVariable Long id,  @RequestBody @Validated ViaggioPayload viaggioPayload) {
        try {
            Viaggio viaggio = viaggioService.updateViaggio(id, viaggioPayload);
            return ResponseEntity.ok(viaggio);
        } catch (ViaggioNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteViaggio(@PathVariable Long id) {
        try {
            viaggioService.deleteViaggio(id);
            return ResponseEntity.noContent().build();
        } catch (ViaggioNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
