package com.example.U4_W6_GestioneViaggiAziendali.service;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Viaggio;
import com.example.U4_W6_GestioneViaggiAziendali.exception.ViaggioNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.payload.ViaggioPayload;
import com.example.U4_W6_GestioneViaggiAziendali.repository.ViaggioDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ViaggioService {

    @Autowired
    private ViaggioDAORepository viaggioDAO;

    //metodo per inserire un nuovo viaggio
    public Long createViaggio(ViaggioPayload viaggioPayload) {
        Viaggio viaggio = fromDTOToEntity(viaggioPayload);
        return viaggioDAO.save(viaggio).getId();
    }


    //metodo per ottenere un viaggio tramite id
    public ViaggioPayload getViaggio(Long id) {
        Optional<Viaggio> viaggio = viaggioDAO.findById(id);
        if (viaggio.isPresent()) {
            return fromEntityToDTO(viaggio.get());
        } else {
            throw new ViaggioNotFound("Viaggio con id: " + id + " non trovato");
        }
    }

    //metodo per ottenre tutti i viaggi nel database
    public List<ViaggioPayload> getAllViaggi() {
        List<Viaggio> viaggi = viaggioDAO.findAll();
        List<ViaggioPayload> viaggiPayload = new ArrayList<>();
        for(Viaggio viaggio : viaggi){
            viaggiPayload.add(fromEntityToDTO(viaggio));
        }
        return viaggiPayload;
    }

    public ViaggioPayload updateViaggio(Long id, ViaggioPayload viaggioPayload) {
        Viaggio viaggio = viaggioDAO.findById(id)
                .orElseThrow(() -> new ViaggioNotFound("Viaggio con id: " + id + " non trovato"));

        viaggio.setDestinazione(viaggioPayload.getDestinazione());
        viaggio.setDataViaggio(viaggioPayload.getDataViaggio());
        viaggio.setStato(viaggioPayload.getStato());
        viaggio.setPrenotazioni(viaggioPayload.getPrenotazioni());

        return fromEntityToDTO(viaggioDAO.save(viaggio));
    }


    public void deleteViaggio(Long id) {
        Viaggio viaggio = viaggioDAO.findById(id)
                .orElseThrow(() -> new ViaggioNotFound("Viaggio con id: " + id + " non trovato"));

        viaggioDAO.delete(viaggio);
    }

    public ViaggioPayload fromEntityToDTO(Viaggio viaggio) {
        return new ViaggioPayload(
                viaggio.getDestinazione(),
                viaggio.getDataViaggio(),
                viaggio.getStato(),
                viaggio.getPrenotazioni()
        );
    }

    public Viaggio fromDTOToEntity(ViaggioPayload viaggioPayload) {
        return new Viaggio(
                viaggioPayload.getDestinazione(),
                viaggioPayload.getDataViaggio(),
                viaggioPayload.getStato(),
                viaggioPayload.getPrenotazioni()
        );
    }
}
