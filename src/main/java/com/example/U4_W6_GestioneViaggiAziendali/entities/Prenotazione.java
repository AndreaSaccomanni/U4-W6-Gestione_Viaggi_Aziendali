package com.example.U4_W6_GestioneViaggiAziendali.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private Long idDipendente;
    private Long idVaggio;
    private LocalDate data;
    private String note;


    public Prenotazione(Long idDipendente, Long idVaggio, LocalDate data, String note) {
        this.idDipendente = idDipendente;
        this.idVaggio = idVaggio;
        this.data = data;
        this.note = note;
    }
}
