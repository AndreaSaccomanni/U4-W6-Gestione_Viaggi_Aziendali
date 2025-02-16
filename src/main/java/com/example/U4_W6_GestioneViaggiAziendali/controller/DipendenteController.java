package com.example.U4_W6_GestioneViaggiAziendali.controller;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.U4_W6_GestioneViaggiAziendali.payload.DipendentePayload;
import com.example.U4_W6_GestioneViaggiAziendali.payload.PrenotazionePayload;
import com.example.U4_W6_GestioneViaggiAziendali.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private Cloudinary cloudConfig;


    @PostMapping("/new")
    public ResponseEntity<String> createDipendente(@RequestBody @Validated DipendentePayload dipendentePayload, BindingResult validazione) {
        if (validazione.hasErrors()) {
            String messaggioErrore = "ERRORE DI VALIDAZIONE: ";
            for (ObjectError errore : validazione.getAllErrors()) {
                messaggioErrore += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.BAD_REQUEST);
        } else {
            Long idGenerato = dipendenteService.createDipendente(dipendentePayload);
            return new ResponseEntity<>("Dipendente aggiunto correttamente con id: " + idGenerato, HttpStatus.CREATED);
        }
    }


    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImmagineProfilo(@RequestPart("immagineProfilo") MultipartFile immagineProfilo, @RequestPart @Validated DipendentePayload dipendentePayload, BindingResult validazione) {
        if (validazione.hasErrors()) {
            String messaggioErrore = "ERRORE DI VALIDAZIONE: ";
            for (ObjectError errore : validazione.getAllErrors()) {
                messaggioErrore += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.BAD_REQUEST);
        } else {
            String messaggio = "";
            HttpStatus stato;
            try {
                Map mappa = cloudConfig.uploader().upload(immagineProfilo.getBytes(), ObjectUtils.emptyMap());

                String urlImmagineProfilo = mappa.get("secure_url").toString();
                dipendentePayload.setImmagineProfilo(urlImmagineProfilo);
                dipendenteService.createDipendente(dipendentePayload);
                return new ResponseEntity<>(" Dipendente aggiunto correttamente", HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>("Errore nel caricamento del dipendente con l'immagine profilo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
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
    public ResponseEntity<String> creaPrenotazione(@PathVariable Long idDipendente, @RequestBody @Validated PrenotazionePayload prenotazionePayload, BindingResult validazione) {
        if (validazione.hasErrors()) {
            String messaggioErrore = "ERRORE DI VALIDAZIONE: ";
            for (ObjectError errore : validazione.getAllErrors()) {
                messaggioErrore += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrore, HttpStatus.BAD_REQUEST);
        } else {
            dipendenteService.creaPrenotazione(idDipendente, prenotazionePayload.getIdViaggio(), prenotazionePayload.getData(), prenotazionePayload.getNote());
            return new ResponseEntity<>("La prenotazione per il viaggio con id: " + prenotazionePayload.getIdViaggio() + " del dipendente con id: " + idDipendente + " è andata a buon fine", HttpStatus.CREATED);
        }
    }
}
