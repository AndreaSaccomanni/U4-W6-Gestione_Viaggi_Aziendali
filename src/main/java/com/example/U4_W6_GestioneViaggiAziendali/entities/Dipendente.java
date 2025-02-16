package com.example.U4_W6_GestioneViaggiAziendali.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "dipendenti")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dipendente {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String nome;
    private String cognome;

    @Column(unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idDipendente")
    private List<Prenotazione> prenotazioni;

    private String immagineProfilo;



    public Dipendente(String username, String nome, String cognome, String email, List<Prenotazione> prenotazioni, String immagineProfilo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.prenotazioni = prenotazioni;
        this.immagineProfilo = immagineProfilo;
    }

    public Dipendente(String username, String nome, String cognome, String email, List<Prenotazione> prenotazioni) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.prenotazioni = prenotazioni;
    }

    public Dipendente(String username, String nome, String cognome, String email) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
}
