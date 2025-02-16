package com.example.U4_W6_GestioneViaggiAziendali.controller;

import com.example.U4_W6_GestioneViaggiAziendali.payload.ViaggioPayload;
import com.example.U4_W6_GestioneViaggiAziendali.service.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viaggio")
public class ViaggioController {
    @Autowired
    private ViaggioService viaggioService;

    @PostMapping("/new")
    public ResponseEntity<String> createViaggio(@RequestBody @Validated ViaggioPayload viaggioPayload, BindingResult validazione) {
        if (validazione.hasErrors()) {
            String messaggioErrore = "ERRORE DI VALIDAZIONE: ";
            for (ObjectError errore : validazione.getAllErrors()) {
                messaggioErrore += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.BAD_REQUEST);
        } else {
            Long idGenerato = viaggioService.createViaggio(viaggioPayload);
            return new ResponseEntity<>("Il viaggio con destinazione: " + viaggioPayload.getDestinazione() + " è stato inserito correttamente. Id assegnato: " + idGenerato, HttpStatus.CREATED);
        }
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
    public String updateViaggio(@PathVariable Long id, @RequestBody @Validated ViaggioPayload viaggioPayload, BindingResult validazione) {
        if (validazione.hasErrors()) {
            String messaggioErrore = "ERRORE DI VALIDAZIONE";
            for (ObjectError errore : validazione.getAllErrors()) {
                messaggioErrore += errore.getDefaultMessage() + "\n";
            }
            return messaggioErrore;
        } else {
            ViaggioPayload viaggioAggiornato = viaggioService.updateViaggio(id, viaggioPayload);
            return "Il viaggio è stato aggiornato correttamente";
        }
    }

    //POSTMAN -> http:localhost8080/viaggio/updateStato/{id}?nuovoStato={nuovoStato}
    @PutMapping("/updateStato/{id}")
    public String updateStatoViaggio(@PathVariable Long id, @RequestParam String nuovoStato) {
        ViaggioPayload viaggioStatoAggiornato = viaggioService.updateStatoViaggio(id, nuovoStato);
        return "Lo stato del viaggio con id: " + id + " è stato aggiornato correttamente";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteViaggio(@PathVariable Long id) {
        viaggioService.deleteViaggio(id);
        return "Il viaggio  è stato  rimosso correttammente dal sistema";
    }
}
