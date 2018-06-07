package municipalidad.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import municipalidad.service.TramiteService;
import municipalidad.domain.Tramite;
import municipalidad.repository.TramiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import municipalidad.domain.ArchivosDetalle;
import municipalidad.domain.User;
import municipalidad.domain.enumeration.EstadoPresentacion;
import municipalidad.repository.ArchivosDetalleRepository;
import municipalidad.repository.ArchivosRepository;
import municipalidad.service.ArchivosDetalleService;
import municipalidad.service.MailService;
import municipalidad.service.dto.TramiteDTO;

/**
 * Service Implementation for managing Tramite.
 */
@Service
@Transactional
public class TramiteServiceImpl implements TramiteService {

    private final Logger log = LoggerFactory.getLogger(TramiteServiceImpl.class);
    private final ArchivosDetalleService archivosDetalleService;
    private final TramiteRepository tramiteRepository;
    private final ArchivosRepository archivosRepository;
    private final MailService mailService;

    public TramiteServiceImpl(ArchivosDetalleService archivosDetalleService, TramiteRepository tramiteRepository, ArchivosRepository archivosRepository, MailService mailService) {
        this.archivosDetalleService = archivosDetalleService;
        this.tramiteRepository = tramiteRepository;
        this.archivosRepository = archivosRepository;
        this.mailService = mailService;
    }



    /**
     * Save a tramite.
     *
     * @param tramite the entity to save
     * @return the persisted entity
     */
    @Override
    public Tramite save(Tramite tramite) {
        Tramite retorno = tramiteRepository.save(tramite);
        retorno.setFechaFin(ZonedDateTime.now());
        archivosDetalleService.save(tramite.getArchivosDetalle());
        User email = tramite.getArchivosDetalle().getPresentacion().getEscribania().getUsuario();
        mailService.sendNotificationMail(email);
        
        log.debug("Request to save Tramite : {}", tramite);
        return retorno;
    }

    /**
     * Get all the tramites.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TramiteDTO> findAll() {
        log.debug("Request to get all Tramites");
        List<Tramite> tramites = tramiteRepository.findAll();
        List<TramiteDTO> retorno = new ArrayList<>();
        for (Tramite tramite : tramites) {
            retorno.add(getConvertDTOFromTramite(tramite));
        }
        return retorno;
    }

    /**
     * Get one tramite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TramiteDTO findOne(Long id) {
        log.debug("Request to get Tramite : {}", id);
        return getConvertDTOFromTramite(tramiteRepository.findOne(id));
    }

    /**
     * Delete the tramite by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tramite : {}", id);
        tramiteRepository.delete(id);
    }

    ////////////////////////////////////////////////////////////////////////
    
    
    public TramiteDTO getConvertDTOFromTramite(Tramite tramite) {

        TramiteDTO dto = new TramiteDTO();
        dto.setArchivos(archivosRepository.findByTramite(tramite));
        dto.setFecha(tramite.getFecha());
        dto.setFechaFin(tramite.getFechaFin());
        dto.setObservaciones(tramite.getObservaciones());
        dto.setArchivosDetalle(tramite.getArchivosDetalle());
        dto.setId(tramite.getId());

        
        return dto;
    }
    
}
