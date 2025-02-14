package com.example.U4_W6_GestioneViaggiAziendali.controller;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Dipendente;
import com.example.U4_W6_GestioneViaggiAziendali.exception.DipendenteNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.payload.DipendentePayload;
import com.example.U4_W6_GestioneViaggiAziendali.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping
    public ResponseEntity<Dipendente> createDipendente(@RequestBody DipendentePayload dipendentePayload) {
        Dipendente dipendente = dipendenteService.createDipendente(dipendentePayload);
        return new ResponseEntity<>(dipendente, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dipendente> getDipendente(@PathVariable Long id) {
        try {
            Dipendente dipendente = dipendenteService.getDipendente(id);
            return ResponseEntity.ok(dipendente);
        } catch (DipendenteNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<Dipendente> getAllDipendenti() {
        return dipendenteService.getAllDipendenti();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dipendente> updateDipendente(@PathVariable Long id, @Validated @RequestBody DipendentePayload dipendentePayload) {
        try {
            Dipendente dipendente = dipendenteService.updateDipendente(id, dipendentePayload);
            return ResponseEntity.ok(dipendente);
        } catch (DipendenteNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDipendente(@PathVariable Long id) {
        dipendenteService.deleteDipendente(id);
        return ResponseEntity.noContent().build();
    }
}
