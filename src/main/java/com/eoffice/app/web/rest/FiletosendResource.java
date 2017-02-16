package com.eoffice.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.eoffice.app.service.FiletosendService;
import com.eoffice.app.web.rest.util.HeaderUtil;
import com.eoffice.app.service.dto.FiletosendDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for managing Filetosend.
 */
@RestController
@RequestMapping("/api")
public class FiletosendResource {

    private final Logger log = LoggerFactory.getLogger(FiletosendResource.class);

    private static final String ENTITY_NAME = "filetosend";
        
    private final FiletosendService filetosendService;

    public FiletosendResource(FiletosendService filetosendService) {
        this.filetosendService = filetosendService;
    }

    /**
     * POST  /filetosends : Create a new filetosend.
     *
     * @param filetosendDTO the filetosendDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filetosendDTO, or with status 400 (Bad Request) if the filetosend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filetosends")
    @Timed
    public ResponseEntity<FiletosendDTO> createFiletosend(@Valid @RequestBody FiletosendDTO filetosendDTO) throws URISyntaxException {
        log.debug("REST request to save Filetosend : {}", filetosendDTO);
        if (filetosendDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new filetosend cannot already have an ID")).body(null);
        }
        FiletosendDTO result = filetosendService.save(filetosendDTO);
        return ResponseEntity.created(new URI("/api/filetosends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filetosends : Updates an existing filetosend.
     *
     * @param filetosendDTO the filetosendDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filetosendDTO,
     * or with status 400 (Bad Request) if the filetosendDTO is not valid,
     * or with status 500 (Internal Server Error) if the filetosendDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filetosends")
    @Timed
    public ResponseEntity<FiletosendDTO> updateFiletosend(@Valid @RequestBody FiletosendDTO filetosendDTO) throws URISyntaxException {
        log.debug("REST request to update Filetosend : {}", filetosendDTO);
        if (filetosendDTO.getId() == null) {
            return createFiletosend(filetosendDTO);
        }
        FiletosendDTO result = filetosendService.save(filetosendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filetosendDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filetosends : get all the filetosends.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of filetosends in body
     */
    @GetMapping("/filetosends")
    @Timed
    public List<FiletosendDTO> getAllFiletosends() {
        log.debug("REST request to get all Filetosends");
        return filetosendService.findAll();
    }

    /**
     * GET  /filetosends/:id : get the "id" filetosend.
     *
     * @param id the id of the filetosendDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filetosendDTO, or with status 404 (Not Found)
     */
    @GetMapping("/filetosends/{id}")
    @Timed
    public ResponseEntity<FiletosendDTO> getFiletosend(@PathVariable String id) {
        log.debug("REST request to get Filetosend : {}", id);
        FiletosendDTO filetosendDTO = filetosendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(filetosendDTO));
    }

    /**
     * DELETE  /filetosends/:id : delete the "id" filetosend.
     *
     * @param id the id of the filetosendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filetosends/{id}")
    @Timed
    public ResponseEntity<Void> deleteFiletosend(@PathVariable String id) {
        log.debug("REST request to delete Filetosend : {}", id);
        filetosendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
