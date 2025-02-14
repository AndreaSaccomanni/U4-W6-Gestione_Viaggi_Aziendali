package com.example.U4_W6_GestioneViaggiAziendali.repository;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DipendenteDAORepository extends JpaRepository<Dipendente, Long> {

    Dipendente findByUsername(String usernme);

    Dipendente findByEmail(String email);
}
