package org.fol37.quinzainedulivre.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fol37.quinzainedulivre.domain.Age;
import org.fol37.quinzainedulivre.domain.Categorie;
import org.fol37.quinzainedulivre.domain.Editeur;
import org.fol37.quinzainedulivre.domain.Livre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
    private LivreResource livreResource;
    @Inject
    private EditeurResource editeurResource;
    @Inject
    private CategorieResource categorieResource;
    @Inject
    private AgeResource ageResource;

    /**
     * GET  /livres -> get all the livres.
     */
    @RequestMapping(value = "/livres",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Livre> getAllLivres() throws URISyntaxException {
        return livreResource.getAllLivres();
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
        return categorieResource.getAllCategories();
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
        return editeurResource.getAllEditeurs();
    }

    /**
     * GET  /ages -> get all the ages.
     */
    @RequestMapping(value = "/ages",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Age> getAllAges() {
        return ageResource.getAllAges();
    }

}
