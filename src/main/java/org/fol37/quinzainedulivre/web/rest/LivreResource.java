package org.fol37.quinzainedulivre.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fol37.quinzainedulivre.domain.Livre;
import org.fol37.quinzainedulivre.repository.LivreRepository;
import org.fol37.quinzainedulivre.web.rest.util.HeaderUtil;
import org.fol37.quinzainedulivre.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Livre.
 */
@RestController
@RequestMapping("/api")
public class LivreResource {

    private final Logger log = LoggerFactory.getLogger(LivreResource.class);

    @Inject
    private LivreRepository livreRepository;

    /**
     * POST  /livres -> Create a new livre.
     */
    @RequestMapping(value = "/livres",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) throws URISyntaxException {
        log.debug("REST request to save Livre : {}", livre);
        if (livre.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new livre cannot already have an ID").body(null);
        }
        Livre result = livreRepository.save(livre);
        return ResponseEntity.created(new URI("/api/livres/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("livre", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /livres -> Updates an existing livre.
     */
    @RequestMapping(value = "/livres",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Livre> updateLivre(@RequestBody Livre livre) throws URISyntaxException {
        log.debug("REST request to update Livre : {}", livre);
        if (livre.getId() == null) {
            return createLivre(livre);
        }
        Livre result = livreRepository.save(livre);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("livre", livre.getId().toString()))
                .body(result);
    }


    /**
     * GET  /livres -> get all the livres.
     */
    @RequestMapping(value = "/livres",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Livre>> getAllLivresByBackOffice(Pageable pageable)
            throws URISyntaxException {
        Page<Livre> page = livreRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/livres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /livres -> get all the livres.
     */
    @RequestMapping(value = "/allLivres",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Livre>> getAllLivres(Pageable pageable)
        throws URISyntaxException {
        List<Livre> page = livreRepository.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /livres/:id -> get the "id" livre.
     */
    @RequestMapping(value = "/livres/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Livre> getLivre(@PathVariable Long id) {
        log.debug("REST request to get Livre : {}", id);
        return Optional.ofNullable(livreRepository.findOne(id))
            .map(livre -> new ResponseEntity<>(
                livre,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /livres/:id -> delete the "id" livre.
     */
    @RequestMapping(value = "/livres/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLivre(@PathVariable Long id) {
        log.debug("REST request to delete Livre : {}", id);
        livreRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("livre", id.toString())).build();
    }
}
