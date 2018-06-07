package municipalidad.service.impl;

import java.util.ArrayList;
import municipalidad.service.ArchivosDetalleService;
import municipalidad.domain.ArchivosDetalle;
import municipalidad.repository.ArchivosDetalleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import municipalidad.service.dto.ArchivosDetalleDTO;

/**
 * Service Implementation for managing ArchivosDetalle.
 */
@Service
@Transactional
public class ArchivosDetalleServiceImpl implements ArchivosDetalleService {

    private final Logger log = LoggerFactory.getLogger(ArchivosDetalleServiceImpl.class);

    private final ArchivosDetalleRepository archivosDetalleRepository;

    public ArchivosDetalleServiceImpl(ArchivosDetalleRepository archivosDetalleRepository) {
        this.archivosDetalleRepository = archivosDetalleRepository;
    }

    /**
     * Save a archivosDetalle.
     *
     * @param archivosDetalle the entity to save
     * @return the persisted entity
     */
    @Override
    public ArchivosDetalle save(ArchivosDetalle archivosDetalle) {
        log.debug("Request to save ArchivosDetalle : {}", archivosDetalle);
        return archivosDetalleRepository.save(archivosDetalle);
    }

    /**
     * Get all the archivosDetalles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArchivosDetalleDTO> findAll() {
        List<ArchivosDetalleDTO> archivosDetalle = new ArrayList<>();
        
        
        
        log.debug("Request to get all ArchivosDetalles");
//        return archivosDetalleRepository.findAll();
        return archivosDetalle;
    }

    /**
     * Get one archivosDetalle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ArchivosDetalle findOne(Long id) {
        log.debug("Request to get ArchivosDetalle : {}", id);
        return archivosDetalleRepository.findOne(id);
    }

    /**
     * Delete the archivosDetalle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArchivosDetalle : {}", id);
        archivosDetalleRepository.delete(id);
    }

   
}
