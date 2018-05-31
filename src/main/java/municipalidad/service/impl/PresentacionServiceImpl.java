package municipalidad.service.impl;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import municipalidad.service.PresentacionService;
import municipalidad.domain.Presentacion;
import municipalidad.repository.PresentacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import municipalidad.domain.Archivos;
import municipalidad.domain.ArchivosDetalle;
import municipalidad.domain.Escribania;
import municipalidad.domain.Tramite;
import municipalidad.domain.User;
import municipalidad.domain.enumeration.EstadoPresentacion;
import municipalidad.repository.ArchivosDetalleRepository;
import municipalidad.repository.ArchivosRepository;
import municipalidad.repository.EscribaniaRepository;
import municipalidad.repository.TramiteRepository;
import municipalidad.service.UserService;
import municipalidad.service.dto.PresentacionDTO;
import municipalidad.service.dto.TramiteDTO;

/**
 * Service Implementation for managing Presentacion.
 */
@Service
@Transactional
public class PresentacionServiceImpl implements PresentacionService {

    private final Logger log = LoggerFactory.getLogger(PresentacionServiceImpl.class);

    private final PresentacionRepository presentacionRepository;
    private final ArchivosDetalleRepository archivosDetalleRepository;
    private final TramiteRepository tramiteRepository;
    private final ArchivosRepository archivosRepository;
    private final UserService userService;
    private final EscribaniaRepository escribaniaRepository;

    public PresentacionServiceImpl(PresentacionRepository presentacionRepository, ArchivosDetalleRepository archivosDetalleRepository, TramiteRepository tramiteRepository, ArchivosRepository archivosRepository, UserService userService, EscribaniaRepository escribaniaRepository) {
        this.presentacionRepository = presentacionRepository;
        this.archivosDetalleRepository = archivosDetalleRepository;
        this.tramiteRepository = tramiteRepository;
        this.archivosRepository = archivosRepository;
        this.userService = userService;
        this.escribaniaRepository = escribaniaRepository;
    }
    
    /**
     * Get all the presentacions.
     * TRAE TODAS LAS PRESENTACIONES FILTRADAS POR EL USUARIO QUE ESTA LOGGEADO
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PresentacionDTO> findAll() {
        //OBTIENE EL USUARIO LOGUEADO
        Optional<User> user = userService.getUserWithAuthorities();
        //BUSCA EL USUARIO QUE CORRESPONDE A LA ESCRIBANIA
        Escribania escribania = (escribaniaRepository.findByUsuario(user.get()));
        //TRAE TODAS LAS PRESENTACIONES QUE TENGA ESA ESCRIBANIA
        List<Presentacion> presentaciones = presentacionRepository.findByEscribania(escribania);
        List<PresentacionDTO> retorno = new ArrayList<>();
        //HACE EL OBJETO QUE VA A MANDAR A LA LISTA
        PresentacionDTO dto = new PresentacionDTO();

        for (Presentacion presentacion : presentaciones) {
            dto.setApellido(presentacion.getApellido());
            dto.setNombre(presentacion.getNombre());
            dto.setCuitEscribano(presentacion.getCuitEscribano());
            dto.setId(presentacion.getId());
            dto.setEscribania(presentacion.getEscribania());

            dto.setArchivosDetalle(archivosDetalleRepository.findByPresentacion(presentacion));
            for (ArchivosDetalle detalle : dto.getArchivosDetalle()) {
                dto.setTramite(tramiteRepository.findByArchivosDetalle(detalle));
                for (Tramite detalle1 : dto.getTramite()) {
                    dto.setArchivo(archivosRepository.findByTramite(detalle1));
                }
            }

            
            retorno.add(dto);
            dto = new PresentacionDTO();
        }

        log.debug("Request to get all Presentacions");
        return retorno;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Presentacion findOne(Long id) {
        log.debug("Request to get Presentacion : {}", id);
        return presentacionRepository.findOne(id);
    }

    /**
     * Delete the presentacion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Presentacion : {}", id);
        presentacionRepository.delete(id);
    }

    //GAURDA TODO LA PRESENTACION Y CREA TODOS LOS DEMAS OBJETOS//
    @Override
    public Presentacion saveDTO(PresentacionDTO dto) {
        log.debug("Request to save Presentacion : {}", dto);
        Presentacion presentacion = presentacionRepository.save(convertPresentacionFromDTO(dto));
        ArchivosDetalle archivosDetalle = getNewArchivosDetalle(presentacion);
        Tramite tramite = getNewTramite(archivosDetalle);
        getArchivosFromDTOAndTramite(dto, tramite);
        return presentacion;
    }

    //INICIA EL ARCHIVODETALLE//
    public ArchivosDetalle getNewArchivosDetalle(Presentacion presentacion) {
        ArchivosDetalle retorno = new ArchivosDetalle();
        retorno.setEstado(EstadoPresentacion.ENTREGADO);
        retorno.setPresentacion(presentacion);
        retorno = archivosDetalleRepository.save(retorno);

        return retorno;
    }

    //INICIA EL TRAMITE//
    public Tramite getNewTramite(ArchivosDetalle archivosDetalle) {
        Tramite retorno = new Tramite();

        retorno.setFecha(ZonedDateTime.now());
        retorno.setArchivosDetalle(archivosDetalle);
        retorno = tramiteRepository.save(retorno);

        return retorno;
    }

    //ESTO TENDRIA QUE RECIBIR EL ARRAY DE ARCHIVOS QUE MANDA EL FRONT Y SETEARLOS AL TRAMITE//
    public void getArchivosFromDTOAndTramite(PresentacionDTO dto, Tramite tramite) {
        Archivos archivo = new Archivos();

        for (Archivos arch : dto.getArchivo()) {
            archivo.setArchivo(arch.getArchivo());
            archivo.setArchivoContentType(arch.getArchivoContentType());
            archivo.setTramite(tramite);
            archivosRepository.save(archivo);
            archivo = new Archivos();
        }

    }

    public Presentacion convertPresentacionFromDTO(PresentacionDTO dto) {
        Presentacion presentacion = new Presentacion();

        Optional<User> user = userService.getUserWithAuthorities();
        dto.setUser(user.get());
        dto.setEscribania(escribaniaRepository.findByUsuario(dto.getUser()));

        presentacion.setEscribania(dto.getEscribania());
        presentacion.setApellido(dto.getApellido());
        presentacion.setCuitEscribano(dto.getCuitEscribano());
        presentacion.setNombre(dto.getNombre());
        presentacion.setId(dto.getId());

        return presentacion;
    }

    @Override
    public Presentacion save(Presentacion presentacion) {
        return null;
    }
}
