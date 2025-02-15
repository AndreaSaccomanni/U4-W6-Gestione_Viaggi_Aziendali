package com.example.U4_W6_GestioneViaggiAziendali.controller;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Viaggio;
import com.example.U4_W6_GestioneViaggiAziendali.exception.ViaggioNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.payload.ViaggioPayload;
import com.example.U4_W6_GestioneViaggiAziendali.service.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viaggio")
public class ViaggioController {
    @Autowired
    private ViaggioService viaggioService;

    @PostMapping("/new")
    public String createViaggio(@RequestBody @Validated ViaggioPayload viaggioPayload) {
        Long idGenerato = viaggioService.createViaggio(viaggioPayload);
        return "Il viaggio con destinazione: " + viaggioPayload.getDestinazione() + " è stato inserito correttamente. Id assegnato: " + idGenerato;
    }

    //singolo viaggio
    @GetMapping("/{id}")
    public ViaggioPayload getViaggio(@PathVariable("id") Long id) {
        return viaggioService.getViaggio(id);
    }

    //tutti i viaggi
    @GetMapping("/all")
    public List<ViaggioPayload> getAllViaggi() {
        return viaggioService.getAllViaggi();
    }

    @PutMapping("/update/{id}")
    public String updateViaggio(@PathVariable Long id,  @RequestBody @Validated ViaggioPayload viaggioPayload) {
        ViaggioPayload viaggioAggiornato = viaggioService.updateViaggio(id, viaggioPayload);
        return "Il viaggio è stato aggiornato correttamente";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteViaggio(@PathVariable Long id) {
        viaggioService.deleteViaggio(id);
        return "Il viaggio  è stato  rimosso correttammente dal sistema";
    }
}
