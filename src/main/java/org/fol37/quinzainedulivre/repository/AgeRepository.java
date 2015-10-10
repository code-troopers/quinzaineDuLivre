package org.fol37.quinzainedulivre.repository;

import org.fol37.quinzainedulivre.domain.Age;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Age entity.
 */
public interface AgeRepository extends JpaRepository<Age, Long> {

    List<Age> findByLibelle(String libelle);

}
