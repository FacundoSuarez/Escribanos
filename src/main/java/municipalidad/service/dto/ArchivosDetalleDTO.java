package municipalidad.service.dto;

import java.util.List;
import municipalidad.domain.Archivos;
import municipalidad.domain.ArchivosDetalle;
import municipalidad.domain.Tramite;

/**
 *
 * @author Facundo
 */
public class ArchivosDetalleDTO {
    
    private List<Tramite> tramites;
    private List<Archivos> archivos;
    private List<ArchivosDetalle> archivosDetalle;

    public List<Tramite> getTramites() {
        return tramites;
    }

    public void setTramites(List<Tramite> tramites) {
        this.tramites = tramites;
    }

    public List<Archivos> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<Archivos> archivos) {
        this.archivos = archivos;
    }

    public List<ArchivosDetalle> getArchivosDetalle() {
        return archivosDetalle;
    }

    public void setArchivosDetalle(List<ArchivosDetalle> archivosDetalle) {
        this.archivosDetalle = archivosDetalle;
    }
    
    
    
    
    
    
    
    
    
    
}
