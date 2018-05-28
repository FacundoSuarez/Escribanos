package municipalidad.service.dto;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import municipalidad.domain.Archivos;
import municipalidad.domain.ArchivosDetalle;
import municipalidad.domain.Operador;

public class TramiteDTO {

    private Long id;

    private ZonedDateTime fecha;

    private ZonedDateTime fechaFin;

    private String observaciones;

    private List<Archivos> archivos;

    private ArchivosDetalle archivosDetalle;

    private Operador operador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public ZonedDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public ArchivosDetalle getArchivosDetalle() {
        return archivosDetalle;
    }

    public void setArchivosDetalle(ArchivosDetalle archivosDetalle) {
        this.archivosDetalle = archivosDetalle;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public List<Archivos> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<Archivos> archivos) {
        this.archivos = archivos;
    }
    
}
