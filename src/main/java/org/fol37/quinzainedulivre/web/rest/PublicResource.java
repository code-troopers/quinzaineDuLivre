package org.fol37.quinzainedulivre.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fol37.quinzainedulivre.domain.Age;
import org.fol37.quinzainedulivre.domain.Categorie;
import org.fol37.quinzainedulivre.domain.Editeur;
import org.fol37.quinzainedulivre.domain.Livre;
import org.fol37.quinzainedulivre.repository.AgeRepository;
import org.fol37.quinzainedulivre.repository.CategorieRepository;
import org.fol37.quinzainedulivre.repository.EditeurRepository;
import org.fol37.quinzainedulivre.repository.LivreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Livre.
 */
@RestController
@RequestMapping("/api/public")
public class PublicResource {

    private final Logger log = LoggerFactory.getLogger(PublicResource.class);

    @Inject
    private LivreRepository livreRepository;
    @Inject
    private EditeurRepository editeurRepository;
    @Inject
    private CategorieRepository categorieRepository;
    @Inject
    private AgeRepository ageRepository;

    /**
     * GET  /livres -> get all the livres.
     */
    @RequestMapping(value = "/livres",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Livre>> getAllLivres(Pageable pageable)
            throws URISyntaxException {
        List<Livre> page = livreRepository.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /categories -> get all the categories.
     */
    @RequestMapping(value = "/categories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Categorie> getAllCategories() {
        log.debug("REST request to get all Categories");
        return categorieRepository.findAll();
    }


    /**
     * GET  /editeurs -> get all the editeurs.
     */
    @RequestMapping(value = "/editeurs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Editeur> getAllEditeurs() {
        log.debug("REST request to get all Editeurs");
        return editeurRepository.findAll();
    }

    /**
     * GET  /ages -> get all the ages.
     */
    @RequestMapping(value = "/ages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Age> getAllAges() {
        log.debug("REST request to get all Ages");
        return ageRepository.findAll();
    }

}
