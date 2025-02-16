package com.example.U4_W6_GestioneViaggiAziendali.payload;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Prenotazione;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DipendentePayload {
    @NotBlank(message = "Il campo 'username' non può essere vuoto")
    private String username;

    @NotBlank(message = "Il campo 'nome' non può essere vuoto")
    private String nome;

    @NotBlank(message = "Il campo 'cognome' non può essere vuoto")
    private String cognome;

    @NotBlank(message = "Il campo 'email' non può essere vuoto")
    @Email(message = "Inserire una email valida")
    private String email;

    private String immagineProfilo;


    private List<Prenotazione> prenotazioni;

    public DipendentePayload(String username, String nome, String cognome, String email,List<Prenotazione> prenotazioni,String immagineProfilo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.prenotazioni = prenotazioni;
        this.immagineProfilo = immagineProfilo;
    }

    public DipendentePayload(String username, String nome, String cognome, String email) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public DipendentePayload(String username, String nome, String cognome, String email, List<Prenotazione> prenotazioni) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.prenotazioni = prenotazioni;
    }

}



