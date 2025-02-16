package com.example.U4_W6_GestioneViaggiAziendali.service;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Prenotazione;
import com.example.U4_W6_GestioneViaggiAziendali.entities.Viaggio;
import com.example.U4_W6_GestioneViaggiAziendali.exception.ViaggioNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.payload.DipendentePayload;
import com.example.U4_W6_GestioneViaggiAziendali.entities.Dipendente;
import com.example.U4_W6_GestioneViaggiAziendali.exception.DipendenteNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.payload.PrenotazionePayload;
import com.example.U4_W6_GestioneViaggiAziendali.repository.DipendenteDAORepository;
import com.example.U4_W6_GestioneViaggiAziendali.repository.PrenotazioneDAORepository;
import com.example.U4_W6_GestioneViaggiAziendali.repository.ViaggioDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteDAORepository dipendenteDAO;

    @Autowired
    private ViaggioDAORepository viaggioDAO;

    @Autowired
    private PrenotazioneDAORepository prenotazioneDAO;


    public Long createDipendente(DipendentePayload dipendentePayload) {
        Dipendente dipendente = fromDTOToEntity(dipendentePayload);
        return dipendenteDAO.save(dipendente).getId();
    }

    public DipendentePayload getDipendente(Long id) {
        Optional<Dipendente> dipendenteTrovato = dipendenteDAO.findById(id);
        if(dipendenteTrovato.isPresent()){
            Dipendente dipendenteTrovatoEntity = dipendenteTrovato.get();
            return fromEntityToDTO(dipendenteTrovatoEntity);
        }else{
            throw  new DipendenteNotFound("Il dipendente con id: " + id + " non è presente nel sistema");
        }

    }

    public List<DipendentePayload> getAllDipendenti() {
        List<Dipendente> dipendenti =  dipendenteDAO.findAll();
        List<DipendentePayload> dipendentiPayload = new ArrayList<>();
        for(Dipendente dipendente : dipendenti){
            dipendentiPayload.add(fromEntityToDTO(dipendente));
        }
        return dipendentiPayload;
    }


    public DipendentePayload updateDipendente(Long id, DipendentePayload dipendentePayload) {
        Dipendente dipendente = dipendenteDAO.findById(id)
                .orElseThrow(() -> new DipendenteNotFound("Dipendente con id " + id + " non trovato"));
        dipendente.setUsername(dipendentePayload.getUsername());
        dipendente.setNome(dipendentePayload.getNome());
        dipendente.setCognome(dipendentePayload.getCognome());
        dipendente.setEmail(dipendentePayload.getEmail());

        return fromEntityToDTO(dipendenteDAO.save(dipendente));
    }

    public void deleteDipendente(Long id) {
        Optional<Dipendente> dipendenteTrovato = dipendenteDAO.findById(id);
        if(dipendenteTrovato.isPresent()){
            Dipendente dipendenteTrovatoEntity = dipendenteTrovato.get();
            dipendenteDAO.delete(dipendenteTrovatoEntity);
        }else{
            throw  new DipendenteNotFound("Il dipendente con id: " + id + " non è presente nel sistema");
        }
    }

    public PrenotazionePayload creaPrenotazione(Long idDipendente, Long idViaggio, LocalDate dataPrenotazione, String note) {
        //ricerca dipendente nel db
        Dipendente dipendente = dipendenteDAO.findById(idDipendente)
                .orElseThrow(() -> new DipendenteNotFound("Dipendente con id: " + idDipendente + " non trovato"));

        //controllo prenotazione dell'utente per data
        boolean haGiaUnaPrenotazione = dipendente.getPrenotazioni().stream()
                .anyMatch(p -> p.getData().equals(dataPrenotazione));

        if (haGiaUnaPrenotazione) {
            throw new RuntimeException("Il dipendente ha già una prenotazione per questa data.");
        }

        //ricerca viaggio nel db
        Viaggio viaggio = viaggioDAO.findById(idViaggio)
                .orElseThrow(() -> new ViaggioNotFound("Viaggio con id: " + idViaggio + " non trovato"));


        // Crea la nuova prenotazione
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setIdDipendente(dipendente.getId());
        prenotazione.setIdViaggio(viaggio.getId());
        prenotazione.setData(dataPrenotazione);
        prenotazione.setNote(note);

        //prenotazione aggiunta al db
        prenotazioneDAO.save(prenotazione);

        //prenotazione aggiunta alla lista delle prenotazioni del dipendente
        dipendente.getPrenotazioni().add(prenotazione);
        dipendenteDAO.save(dipendente);
        //viaggio agigunto alla lista delle prenotazioni del viaggio
        viaggio.getPrenotazioni().add(prenotazione);
        viaggioDAO.save(viaggio);

        return prenotazioneFromEntityToDTO(prenotazione);
    }


    public DipendentePayload fromEntityToDTO(Dipendente dipendente) {
        return new DipendentePayload(
                dipendente.getUsername(),
                dipendente.getNome(),
                dipendente.getCognome(),
                dipendente.getEmail(),
                dipendente.getPrenotazioni(),
                dipendente.getImmagineProfilo()
        );
    }

    public Dipendente fromDTOToEntity(DipendentePayload dipendentePayload) {
        return new Dipendente(
                dipendentePayload.getUsername(),
                dipendentePayload.getNome(),
                dipendentePayload.getCognome(),
                dipendentePayload.getEmail(),
                dipendentePayload.getPrenotazioni(),
                dipendentePayload.getImmagineProfilo()
        );
    }

    public PrenotazionePayload prenotazioneFromEntityToDTO(Prenotazione prenotazione) {
        return new PrenotazionePayload(
                prenotazione.getIdDipendente(),
                prenotazione.getIdViaggio(),
                prenotazione.getData(),
                prenotazione.getNote()
        );
    }

    public Prenotazione prenotazioneFromDTOToEntity(PrenotazionePayload prenotazionePayload) {
        return new Prenotazione(
                prenotazionePayload.getIdDipendente(),
                prenotazionePayload.getIdViaggio(),
                prenotazionePayload.getData(),
                prenotazionePayload.getNote()
        );
    }
}
