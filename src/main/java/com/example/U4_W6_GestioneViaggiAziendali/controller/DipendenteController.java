package com.example.U4_W6_GestioneViaggiAziendali.controller;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Dipendente;
import com.example.U4_W6_GestioneViaggiAziendali.entities.Prenotazione;
import com.example.U4_W6_GestioneViaggiAziendali.exception.DipendenteNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.payload.DipendentePayload;
import com.example.U4_W6_GestioneViaggiAziendali.payload.PrenotazionePayload;
import com.example.U4_W6_GestioneViaggiAziendali.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;


    @PostMapping("/new")
    public ResponseEntity<String> createDipendente(@RequestBody DipendentePayload dipendentePayload) {
        try {
            Long idGenerato = dipendenteService.createDipendente(dipendentePayload);
            return new ResponseEntity<>("Dipendente aggiunto correttamente con id: " + idGenerato, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>("Errore nell'inserimento del dpendente", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public DipendentePayload getDipendente(@PathVariable Long id) {
        return dipendenteService.getDipendente(id);
    }

    @GetMapping("/all")
    public List<DipendentePayload> getAllDipendenti() {
        return dipendenteService.getAllDipendenti();
    }

    @PutMapping("/update/{id}")
    public String updateDipendente(@PathVariable Long id, @Validated @RequestBody DipendentePayload dipendentePayload) {
        DipendentePayload dipendentAggiornato = dipendenteService.updateDipendente(id, dipendentePayload);
        return "Il dipendente è stato aggiornato correttamente";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDipendente(@PathVariable Long id) {
        dipendenteService.deleteDipendente(id);
        return "Il dipendete è stato rimosso correttamente dal sistema";
    }


    //POSTMAN -> http:localhost8080/dipendenti/{idDipendente}/creaPrenotazione/
    @PostMapping("/{idDipendente}/creaPrenotazione")
    public ResponseEntity<String> creaPrenotazione(@PathVariable Long idDipendente,
                                                   @RequestBody PrenotazionePayload prenotazionePayload) {

        try {
            dipendenteService.creaPrenotazione(idDipendente, prenotazionePayload.getIdViaggio(), prenotazionePayload.getData(), prenotazionePayload.getNote());
            return new ResponseEntity<>("La prenotazione per il viaggio con id: " + prenotazionePayload.getIdViaggio() + " del dipendente con id: " + idDipendente + " è andata a buon fine", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Errore nella prenotazione: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
