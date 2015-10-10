package org.fol37.quinzainedulivre.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fol37.quinzainedulivre.domain.Editeur;
import org.fol37.quinzainedulivre.repository.EditeurRepository;
import org.fol37.quinzainedulivre.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
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
 * REST controller for managing Editeur.
 */
@RestController
@RequestMapping("/api")
public class EditeurResource {

    private final Logger log = LoggerFactory.getLogger(EditeurResource.class);

    @Inject
    private EditeurRepository editeurRepository;

    /**
     * POST  /editeurs -> Create a new editeur.
     */
    @RequestMapping(value = "/editeurs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Editeur> createEditeur(@Valid @RequestBody Editeur editeur) throws URISyntaxException {
        log.debug("REST request to save Editeur : {}", editeur);
        if (editeur.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new editeur cannot already have an ID").body(null);
        }
        Editeur result = editeurRepository.save(editeur);
        return ResponseEntity.created(new URI("/api/editeurs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("editeur", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /editeurs -> Updates an existing editeur.
     */
    @RequestMapping(value = "/editeurs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Editeur> updateEditeur(@Valid @RequestBody Editeur editeur) throws URISyntaxException {
        log.debug("REST request to update Editeur : {}", editeur);
        if (editeur.getId() == null) {
            return createEditeur(editeur);
        }
        Editeur result = editeurRepository.save(editeur);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("editeur", editeur.getId().toString()))
                .body(result);
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
        Sort sort = new Sort(Sort.Direction.ASC, "nom");
        return editeurRepository.findAll(sort);
    }

    /**
     * GET  /editeurs/:id -> get the "id" editeur.
     */
    @RequestMapping(value = "/editeurs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Editeur> getEditeur(@PathVariable Long id) {
        log.debug("REST request to get Editeur : {}", id);
        return Optional.ofNullable(editeurRepository.findOne(id))
            .map(editeur -> new ResponseEntity<>(
                editeur,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /editeurs/:id -> delete the "id" editeur.
     */
    @RequestMapping(value = "/editeurs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEditeur(@PathVariable Long id) {
        log.debug("REST request to delete Editeur : {}", id);
        editeurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("editeur", id.toString())).build();
    }
}
