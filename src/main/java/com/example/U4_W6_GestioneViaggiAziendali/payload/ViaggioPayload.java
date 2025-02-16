package com.example.U4_W6_GestioneViaggiAziendali.payload;

import java.time.LocalDate;
import java.util.List;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Prenotazione;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.stylesheets.LinkStyle;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViaggioPayload {
    @NotBlank(message = "Il campo 'destinazione' non può essere vuoto")
    private String destinazione;
    @NotNull(message = "Il campo  'dataViaggio' non può essere nullo")
    private LocalDate dataViaggio;
    @NotBlank(message = "Il campo 'stato' non può essere vuoto")
    private String stato;

    private List<Prenotazione> prenotazioni;

}
