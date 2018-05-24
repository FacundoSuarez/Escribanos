package municipalidad.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.domain.Presentacion;
import municipalidad.service.PresentacionService;
import municipalidad.web.rest.errors.BadRequestAlertException;
import municipalidad.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import municipalidad.service.dto.PresentacionDTO;

/**
 * REST controller for managing Presentacion.
 */
@RestController
@RequestMapping("/api")
public class PresentacionResource {

    private final Logger log = LoggerFactory.getLogger(PresentacionResource.class);

    private static final String ENTITY_NAME = "presentacion";

    private final PresentacionService presentacionService;

    public PresentacionResource(PresentacionService presentacionService) {
        this.presentacionService = presentacionService;
    }

    /**
     * POST  /presentacions : Create a new presentacion.
     *
     * @param presentacionDTO
     * @return the ResponseEntity with status 201 (Created) and with body the new presentacion, or with status 400 (Bad Request) if the presentacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/presentacions")
    @Timed
    public ResponseEntity<Presentacion> createPresentacion(@RequestBody PresentacionDTO presentacionDTO) throws URISyntaxException {
        log.debug("REST request to save Presentacion : {}", presentacionDTO);
        if (presentacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new presentacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Presentacion result = presentacionService.saveDTO(presentacionDTO);
        return ResponseEntity.created(new URI("/api/presentacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /presentacions : Updates an existing presentacion.
     *
     * @param presentacion the presentacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated presentacion,
     * or with status 400 (Bad Request) if the presentacion is not valid,
     * or with status 500 (Internal Server Error) if the presentacion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/presentacions")
    @Timed
    public ResponseEntity<Presentacion> updatePresentacion(@RequestBody PresentacionDTO presentacion) throws URISyntaxException {
        log.debug("REST request to update Presentacion : {}", presentacion);
        if (presentacion.getId() == null) {
            return createPresentacion(presentacion);
        }
        Presentacion result = presentacionService.saveDTO(presentacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, presentacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /presentacions : get all the presentacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of presentacions in body
     */
    @GetMapping("/presentacions")
    @Timed
    public List<PresentacionDTO> getAllPresentacions() {
        log.debug("REST request to get all Presentacions");
        return presentacionService.findAll();
        }

    /**
     * GET  /presentacions/:id : get the "id" presentacion.
     *
     * @param id the id of the presentacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the presentacion, or with status 404 (Not Found)
     */
    @GetMapping("/presentacions/{id}")
    @Timed
    public ResponseEntity<Presentacion> getPresentacion(@PathVariable Long id) {
        log.debug("REST request to get Presentacion : {}", id);
        Presentacion presentacion = presentacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(presentacion));
    }

    /**
     * DELETE  /presentacions/:id : delete the "id" presentacion.
     *
     * @param id the id of the presentacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/presentacions/{id}")
    @Timed
    public ResponseEntity<Void> deletePresentacion(@PathVariable Long id) {
        log.debug("REST request to delete Presentacion : {}", id);
        presentacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
