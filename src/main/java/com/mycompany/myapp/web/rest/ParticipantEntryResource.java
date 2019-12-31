package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.ParticipantEntry;
import com.mycompany.myapp.repository.ParticipantEntryRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.ParticipantEntry}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ParticipantEntryResource {

    private final Logger log = LoggerFactory.getLogger(ParticipantEntryResource.class);

    private static final String ENTITY_NAME = "participantEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipantEntryRepository participantEntryRepository;

    public ParticipantEntryResource(ParticipantEntryRepository participantEntryRepository) {
        this.participantEntryRepository = participantEntryRepository;
    }

    /**
     * {@code POST  /participant-entries} : Create a new participantEntry.
     *
     * @param participantEntry the participantEntry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participantEntry, or with status {@code 400 (Bad Request)} if the participantEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/participant-entries")
    public ResponseEntity<ParticipantEntry> createParticipantEntry(@RequestBody ParticipantEntry participantEntry) throws URISyntaxException {
        log.debug("REST request to save ParticipantEntry : {}", participantEntry);
        if (participantEntry.getId() != null) {
            throw new BadRequestAlertException("A new participantEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParticipantEntry result = participantEntryRepository.save(participantEntry);
        return ResponseEntity.created(new URI("/api/participant-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /participant-entries} : Updates an existing participantEntry.
     *
     * @param participantEntry the participantEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participantEntry,
     * or with status {@code 400 (Bad Request)} if the participantEntry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participantEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/participant-entries")
    public ResponseEntity<ParticipantEntry> updateParticipantEntry(@RequestBody ParticipantEntry participantEntry) throws URISyntaxException {
        log.debug("REST request to update ParticipantEntry : {}", participantEntry);
        if (participantEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParticipantEntry result = participantEntryRepository.save(participantEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participantEntry.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /participant-entries} : get all the participantEntries.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participantEntries in body.
     */
    @GetMapping("/participant-entries")
    public List<ParticipantEntry> getAllParticipantEntries(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all ParticipantEntries");
        return participantEntryRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /participant-entries/:id} : get the "id" participantEntry.
     *
     * @param id the id of the participantEntry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participantEntry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/participant-entries/{id}")
    public ResponseEntity<ParticipantEntry> getParticipantEntry(@PathVariable Long id) {
        log.debug("REST request to get ParticipantEntry : {}", id);
        Optional<ParticipantEntry> participantEntry = participantEntryRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(participantEntry);
    }

    /**
     * {@code DELETE  /participant-entries/:id} : delete the "id" participantEntry.
     *
     * @param id the id of the participantEntry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/participant-entries/{id}")
    public ResponseEntity<Void> deleteParticipantEntry(@PathVariable Long id) {
        log.debug("REST request to delete ParticipantEntry : {}", id);
        participantEntryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
