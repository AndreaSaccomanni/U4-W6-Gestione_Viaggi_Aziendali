package com.example.U4_W6_GestioneViaggiAziendali.service;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Viaggio;
import com.example.U4_W6_GestioneViaggiAziendali.payload.DipendentePayload;
import com.example.U4_W6_GestioneViaggiAziendali.entities.Dipendente;
import com.example.U4_W6_GestioneViaggiAziendali.exception.DipendenteNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.payload.ViaggioPayload;
import com.example.U4_W6_GestioneViaggiAziendali.repository.DipendenteDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteDAORepository dipendenteRepository;


    public Long createDipendente(DipendentePayload dipendentePayload) {
        Dipendente dipendente = fromDTOToEntity(dipendentePayload);
        return dipendenteRepository.save(dipendente).getId();
    }

    public DipendentePayload getDipendente(Long id) {
        Optional<Dipendente> dipendenteTrovato = dipendenteRepository.findById(id);
        if(dipendenteTrovato.isPresent()){
            Dipendente dipendenteTrovatoEntity = dipendenteTrovato.get();
            return fromEntityToDTO(dipendenteTrovatoEntity);
        }else{
            throw  new DipendenteNotFound("Il dipendente con id: " + id + " non è presente nel sistema");
        }

    }

    public List<DipendentePayload> getAllDipendenti() {
        List<Dipendente> dipendenti =  dipendenteRepository.findAll();
        List<DipendentePayload> dipendentiPayload = new ArrayList<>();
        for(Dipendente dipendente : dipendenti){
            dipendentiPayload.add(fromEntityToDTO(dipendente));
        }
        return dipendentiPayload;
    }


    public DipendentePayload updateDipendente(Long id, DipendentePayload dipendentePayload) {
        Dipendente dipendente = dipendenteRepository.findById(id)
                .orElseThrow(() -> new DipendenteNotFound("Dipendente con id " + id + " non trovato"));
        dipendente.setUsername(dipendentePayload.getUsername());
        dipendente.setNome(dipendentePayload.getNome());
        dipendente.setCognome(dipendentePayload.getCognome());
        dipendente.setEmail(dipendentePayload.getEmail());

        return fromEntityToDTO(dipendenteRepository.save(dipendente));
    }

    public void deleteDipendente(Long id) {
        Optional<Dipendente> dipendenteTrovato = dipendenteRepository.findById(id);
        if(dipendenteTrovato.isPresent()){
            Dipendente dipendenteTrovatoEntity = dipendenteTrovato.get();
            dipendenteRepository.delete(dipendenteTrovatoEntity);
        }else{
            throw  new DipendenteNotFound("Il dipendente con id: " + id + " non è presente nel sistema");
        }
    }


    public DipendentePayload fromEntityToDTO(Dipendente dipendente) {
        return new DipendentePayload(
                dipendente.getUsername(),
                dipendente.getNome(),
                dipendente.getCognome(),
                dipendente.getEmail(),
                dipendente.getPrenotazioni()
        );
    }

    public Dipendente fromDTOToEntity(DipendentePayload dipendentePayload) {
        return new Dipendente(
                dipendentePayload.getUsername(),
                dipendentePayload.getNome(),
                dipendentePayload.getCognome(),
                dipendentePayload.getEmail(),
                dipendentePayload.getPrenotazioni()
        );
    }
}
