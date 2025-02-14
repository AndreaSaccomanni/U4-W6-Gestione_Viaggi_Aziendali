package com.example.U4_W6_GestioneViaggiAziendali.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DipendentePayload {
    @NotBlank(message = "Username non può essere vuoto")
    @Size(min = 4, message = "Username deve contenere almeno 4 caratteri")
    private String username;

    @NotBlank(message = "Il campo nome non può essere vuoto")
    private String nome;

    @NotBlank(message = "Il campo cognome non può essere vuoto")
    private String cognome;

    @NotBlank(message = "Il campo email non può essere vuoto")
    @Email(message = "Inserire una email valida")
    private String email;


}
