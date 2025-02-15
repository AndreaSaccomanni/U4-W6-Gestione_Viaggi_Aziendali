package com.example.U4_W6_GestioneViaggiAziendali.repository;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository

public interface PrenotazioneDAORepository extends JpaRepository<Prenotazione, Long> {



//
//    // Query per verificare se il viaggio è già prenotato per una determinata data
//    @Query("SELECT COUNT(p) FROM Prenotazione p WHERE p.idViaggio = :idViaggio AND p.data = :data")
//    Long controlloPrenotazionePerViaggioEData(@Param("idViaggio") Long idViaggio,
//                                              @Param("data") LocalDate data);
//
//    // Query per verificare se l'utente ha già prenotato una postazione per la stessa data
//    @Query("SELECT COUNT(p) FROM Prenotazione p WHERE p.idDipendente = :idDipendente AND p.data = :data")
//    Long controlloPrenotazionePerDipendenteEData(@Param("idDipendente") Long idDipendente,
//                                                 @Param("data") LocalDate data);
//

}
