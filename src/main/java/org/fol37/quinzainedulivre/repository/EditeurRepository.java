package org.fol37.quinzainedulivre.repository;

import org.fol37.quinzainedulivre.domain.Editeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Editeur entity.
 */
public interface EditeurRepository extends JpaRepository<Editeur,Long> {


    List<Editeur> findByNom(String nom);
}
