package com.example.U4_W6_GestioneViaggiAziendali.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "viaggi")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Viaggio {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String destinazione;
    @Column(nullable = false)
    private LocalDate dataViaggio;
    private String stato;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "idViaggio")
    private List<Prenotazione> prenotazioni;

    public Viaggio(String destinazione, LocalDate dataViaggio, String stato, List<Prenotazione> prenotazioni) {
        this.destinazione = destinazione;
        this.dataViaggio = dataViaggio;
        this.stato = stato;
    }


}
