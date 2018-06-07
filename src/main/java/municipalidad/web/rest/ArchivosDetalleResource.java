package municipalidad.web.rest;

import com.codahale.metrics.annotation.Timed;
import municipalidad.domain.ArchivosDetalle;
import municipalidad.service.ArchivosDetalleService;
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
import municipalidad.service.dto.ArchivosDetalleDTO;

/**
 * REST controller for managing ArchivosDetalle.
 */
@RestController
@RequestMapping("/api")
public class ArchivosDetalleResource {

    private final Logger log = LoggerFactory.getLogger(ArchivosDetalleResource.class);

    private static final String ENTITY_NAME = "archivosDetalle";

    private final ArchivosDetalleService archivosDetalleService;

    public ArchivosDetalleResource(ArchivosDetalleService archivosDetalleService) {
        this.archivosDetalleService = archivosDetalleService;
    }

    /**
     * POST  /archivos-detalles : Create a new archivosDetalle.
     *
     * @param archivosDetalle the archivosDetalle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new archivosDetalle, or with status 400 (Bad Request) if the archivosDetalle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/archivos-detalles")
    @Timed
    public ResponseEntity<ArchivosDetalle> createArchivosDetalle(@RequestBody ArchivosDetalle archivosDetalle) throws URISyntaxException {
        log.debug("REST request to save ArchivosDetalle : {}", archivosDetalle);
        if (archivosDetalle.getId() != null) {
            throw new BadRequestAlertException("A new archivosDetalle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArchivosDetalle result = archivosDetalleService.save(archivosDetalle);
        return ResponseEntity.created(new URI("/api/archivos-detalles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /archivos-detalles : Updates an existing archivosDetalle.
     *
     * @param archivosDetalle the archivosDetalle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated archivosDetalle,
     * or with status 400 (Bad Request) if the archivosDetalle is not valid,
     * or with status 500 (Internal Server Error) if the archivosDetalle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/archivos-detalles")
    @Timed
    public ResponseEntity<ArchivosDetalle> updateArchivosDetalle(@RequestBody ArchivosDetalle archivosDetalle) throws URISyntaxException {
        log.debug("REST request to update ArchivosDetalle : {}", archivosDetalle);
        if (archivosDetalle.getId() == null) {
            return createArchivosDetalle(archivosDetalle);
        }
        ArchivosDetalle result = archivosDetalleService.save(archivosDetalle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, archivosDetalle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /archivos-detalles : get all the archivosDetalles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of archivosDetalles in body
     */
    @GetMapping("/archivos-detalles")
    @Timed
    public List<ArchivosDetalleDTO> getAllArchivosDetalles() {
        log.debug("REST request to get all ArchivosDetalles");
        return archivosDetalleService.findAll();
        }

    /**
     * GET  /archivos-detalles/:id : get the "id" archivosDetalle.
     *
     * @param id the id of the archivosDetalle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the archivosDetalle, or with status 404 (Not Found)
     */
    @GetMapping("/archivos-detalles/{id}")
    @Timed
    public ResponseEntity<ArchivosDetalle> getArchivosDetalle(@PathVariable Long id) {
        log.debug("REST request to get ArchivosDetalle : {}", id);
        ArchivosDetalle archivosDetalle = archivosDetalleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(archivosDetalle));
    }

    /**
     * DELETE  /archivos-detalles/:id : delete the "id" archivosDetalle.
     *
     * @param id the id of the archivosDetalle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/archivos-detalles/{id}")
    @Timed
    public ResponseEntity<Void> deleteArchivosDetalle(@PathVariable Long id) {
        log.debug("REST request to delete ArchivosDetalle : {}", id);
        archivosDetalleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
