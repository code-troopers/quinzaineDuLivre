package org.fol37.quinzainedulivre.repository;

import org.fol37.quinzainedulivre.domain.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Categorie entity.
 */
public interface CategorieRepository extends JpaRepository<Categorie,Long> {

    List<Categorie> findByLibelle(String libelle);
}
