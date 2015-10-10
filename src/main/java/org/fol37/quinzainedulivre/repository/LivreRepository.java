package org.fol37.quinzainedulivre.repository;

import org.fol37.quinzainedulivre.domain.Livre;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Livre entity.
 */
public interface LivreRepository extends JpaRepository<Livre,Long> {

}
