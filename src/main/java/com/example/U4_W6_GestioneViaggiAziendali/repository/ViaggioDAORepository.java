package com.example.U4_W6_GestioneViaggiAziendali.repository;

import com.example.U4_W6_GestioneViaggiAziendali.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ViaggioDAORepository extends JpaRepository<Viaggio, Long> {


}
