package com.example.U4_W6_GestioneViaggiAziendali.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazoni")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Long idDipendente;
    @Column(nullable = false)
    private Long idViaggio;
    @Column(nullable = false, unique = true)
    private LocalDate data;
    @Column(nullable = true)
    private String note;


    public Prenotazione(Long idDipendente, Long idVaggio, LocalDate data, String note) {
        this.idDipendente = idDipendente;
        this.idViaggio = idVaggio;
        this.data = data;
        this.note = note;
    }
}
