package org.fol37.quinzainedulivre.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fol37.quinzainedulivre.domain.Categorie;
import org.fol37.quinzainedulivre.repository.CategorieRepository;
import org.fol37.quinzainedulivre.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Categorie.
 */
@RestController
@RequestMapping("/api")
public class CategorieResource {

    private final Logger log = LoggerFactory.getLogger(CategorieResource.class);

    @Inject
    private CategorieRepository categorieRepository;

    /**
     * POST  /categories -> Create a new categorie.
     */
    @RequestMapping(value = "/categories",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Categorie> createCategorie(@Valid @RequestBody Categorie categorie) throws URISyntaxException {
        log.debug("REST request to save Categorie : {}", categorie);
        if (categorie.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new categorie cannot already have an ID").body(null);
        }
        Categorie result = categorieRepository.save(categorie);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("categorie", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /categories -> Updates an existing categorie.
     */
    @RequestMapping(value = "/categories",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Categorie> updateCategorie(@Valid @RequestBody Categorie categorie) throws URISyntaxException {
        log.debug("REST request to update Categorie : {}", categorie);
        if (categorie.getId() == null) {
            return createCategorie(categorie);
        }
        Categorie result = categorieRepository.save(categorie);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("categorie", categorie.getId().toString()))
                .body(result);
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
     * GET  /categories/:id -> get the "id" categorie.
     */
    @RequestMapping(value = "/categories/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Categorie> getCategorie(@PathVariable Long id) {
        log.debug("REST request to get Categorie : {}", id);
        return Optional.ofNullable(categorieRepository.findOne(id))
            .map(categorie -> new ResponseEntity<>(
                categorie,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /categories/:id -> delete the "id" categorie.
     */
    @RequestMapping(value = "/categories/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        log.debug("REST request to delete Categorie : {}", id);
        categorieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("categorie", id.toString())).build();
    }
}
