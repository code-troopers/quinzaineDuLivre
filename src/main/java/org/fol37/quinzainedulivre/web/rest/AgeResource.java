package org.fol37.quinzainedulivre.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.fol37.quinzainedulivre.domain.Age;
import org.fol37.quinzainedulivre.repository.AgeRepository;
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
 * REST controller for managing Age.
 */
@RestController
@RequestMapping("/api")
public class AgeResource {

    private final Logger log = LoggerFactory.getLogger(AgeResource.class);

    @Inject
    private AgeRepository ageRepository;

    /**
     * POST  /ages -> Create a new age.
     */
    @RequestMapping(value = "/ages",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Age> createAge(@Valid @RequestBody Age age) throws URISyntaxException {
        log.debug("REST request to save Age : {}", age);
        if (age.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new age cannot already have an ID").body(null);
        }
        Age result = ageRepository.save(age);
        return ResponseEntity.created(new URI("/api/ages/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("age", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /ages -> Updates an existing age.
     */
    @RequestMapping(value = "/ages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Age> updateAge(@Valid @RequestBody Age age) throws URISyntaxException {
        log.debug("REST request to update Age : {}", age);
        if (age.getId() == null) {
            return createAge(age);
        }
        Age result = ageRepository.save(age);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("age", age.getId().toString()))
                .body(result);
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

    /**
     * GET  /ages/:id -> get the "id" age.
     */
    @RequestMapping(value = "/ages/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Age> getAge(@PathVariable Long id) {
        log.debug("REST request to get Age : {}", id);
        return Optional.ofNullable(ageRepository.findOne(id))
            .map(age -> new ResponseEntity<>(
                age,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ages/:id -> delete the "id" age.
     */
    @RequestMapping(value = "/ages/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAge(@PathVariable Long id) {
        log.debug("REST request to delete Age : {}", id);
        ageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("age", id.toString())).build();
    }
}
