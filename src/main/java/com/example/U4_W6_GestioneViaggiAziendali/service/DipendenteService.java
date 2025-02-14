package com.example.U4_W6_GestioneViaggiAziendali.service;

import com.example.U4_W6_GestioneViaggiAziendali.payload.DipendentePayload;
import com.example.U4_W6_GestioneViaggiAziendali.entities.Dipendente;
import com.example.U4_W6_GestioneViaggiAziendali.exception.DipendenteNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.repository.DipendenteDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteDAORepository dipendenteRepository;



    public Dipendente createDipendente(@RequestBody @Validated DipendentePayload dipendentePayload) {
        Dipendente dipendente = new Dipendente(dipendentePayload.getUsername(), dipendentePayload.getNome(),
                dipendentePayload.getCognome(), dipendentePayload.getEmail());
        return dipendenteRepository.save(dipendente);
    }

    public Dipendente getDipendente(Long id) {
        return dipendenteRepository.findById(id)
                .orElseThrow(() -> new DipendenteNotFound("Dipendente con id " + id + " non trovato"));
    }

    public List<Dipendente> getAllDipendenti() {
        return dipendenteRepository.findAll();
    }

    public Dipendente updateDipendente(Long id, @Validated DipendentePayload dipendentePayload) {
        Dipendente dipendente = dipendenteRepository.findById(id)
                .orElseThrow(() -> new DipendenteNotFound("Dipendente con id " + id + " non trovato"));
        dipendente.setUsername(dipendentePayload.getUsername());
        dipendente.setNome(dipendentePayload.getNome());
        dipendente.setCognome(dipendentePayload.getCognome());
        dipendente.setEmail(dipendentePayload.getEmail());
        return dipendenteRepository.save(dipendente);
    }

    public void deleteDipendente(Long id) {
        if (!dipendenteRepository.existsById(id)) {
            throw new DipendenteNotFound("Dipendente con id " + id + " non trovato");
        }
        dipendenteRepository.deleteById(id);
    }
}
