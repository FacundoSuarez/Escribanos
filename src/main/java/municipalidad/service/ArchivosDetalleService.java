package municipalidad.service;

import municipalidad.domain.ArchivosDetalle;
import java.util.List;
import municipalidad.service.dto.ArchivosDetalleDTO;

/**
 * Service Interface for managing ArchivosDetalle.
 */
public interface ArchivosDetalleService {

    /**
     * Save a archivosDetalle.
     *
     * @param archivosDetalle the entity to save
     * @return the persisted entity
     */
    ArchivosDetalle save(ArchivosDetalle archivosDetalle);

    /**
     * Get all the archivosDetalles.
     *
     * @return the list of entities
     */
    List<ArchivosDetalleDTO> findAll();

    /**
     * Get the "id" archivosDetalle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ArchivosDetalle findOne(Long id);

    /**
     * Delete the "id" archivosDetalle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
    
    
}
