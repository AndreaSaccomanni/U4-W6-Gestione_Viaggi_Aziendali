package com.example.U4_W6_GestioneViaggiAziendali.payload;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazionePayload {
    @NotNull(message = "Il campo 'idDipendente' non può essere vuoto")
    private Long idDipendente;
    @NotNull(message = "Il campo 'idViaggio' non può essere vuoto")
    private Long idViaggio;

    private LocalDate data;

    private String note;

}
