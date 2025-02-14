package com.example.U4_W6_GestioneViaggiAziendali.service;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Viaggio;
import com.example.U4_W6_GestioneViaggiAziendali.exception.ViaggioNotFound;
import com.example.U4_W6_GestioneViaggiAziendali.payload.ViaggioPayload;
import com.example.U4_W6_GestioneViaggiAziendali.repository.ViaggioDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViaggioService {

    @Autowired
    private ViaggioDAORepository viaggioDAO;

    public Viaggio createViaggio(ViaggioPayload viaggioPayload) {
        Viaggio viaggio = new Viaggio();
        viaggio.setDestinazione(viaggioPayload.getDestinazione());
        viaggio.setDataViaggio(viaggioPayload.getDataViaggio());
        viaggio.setStato(viaggioPayload.getStato());
        return viaggioDAO.save(viaggio);
    }


    public Viaggio getViaggio(Long id) throws ViaggioNotFound {
        return viaggioDAO.findById(id).orElseThrow(() -> new ViaggioNotFound("Viaggio non trovato"));
    }


    public List<Viaggio> getAllViaggi() {
        return viaggioDAO.findAll();
    }

    public Viaggio updateViaggio(Long id, ViaggioPayload viaggioPayload) throws ViaggioNotFound {
        Viaggio viaggio = getViaggio(id);
        viaggio.setDestinazione(viaggioPayload.getDestinazione());
        viaggio.setDataViaggio(viaggioPayload.getDataViaggio());
        viaggio.setStato(viaggioPayload.getStato());
        return viaggioDAO.save(viaggio);
    }

    public void deleteViaggio(Long id) throws ViaggioNotFound {
        Viaggio viaggio = getViaggio(id);
        viaggioDAO.delete(viaggio);
    }

    public ViaggioPayload fromEntityToDTO(Viaggio viaggio) {
        return new ViaggioPayload(
                viaggio.getDestinazione(),
                viaggio.getDataViaggio(),
                viaggio.getStato()
        );
    }

    public Viaggio fromDTOToEntity(ViaggioPayload viaggioPayload) {
        return new Viaggio(

                viaggioPayload.getDestinazione(),
                viaggioPayload.getDataViaggio(),
                viaggioPayload.getStato()
        );
    }
}
