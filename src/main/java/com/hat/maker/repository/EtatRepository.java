package com.hat.maker.repository;

import com.hat.maker.model.Etat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatRepository extends JpaRepository<Etat, Long> {
    @Query("SELECT " +
            "CASE WHEN COUNT(e) > 0 " +
            "THEN TRUE " +
            "ELSE FALSE " +
            "END " +
            "FROM Etat e " +
            "WHERE LOWER(e.nom) = LOWER(:nom)" +
            "AND e.deleted = FALSE")
    boolean existsByNomIgnoreCaseAndIsNotDeleted(@Param("nom") String nom);
}