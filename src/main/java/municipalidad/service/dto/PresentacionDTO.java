package municipalidad.service.dto;
import java.util.List;
import municipalidad.domain.Archivos;
import municipalidad.domain.ArchivosDetalle;
import municipalidad.domain.Escribania;
import municipalidad.domain.Tramite;
import municipalidad.domain.User;
/**
 *
 * @author Facundo
 */
public class PresentacionDTO {
    
    private Long id;
    private String cuitEscribano;
    private String nombre;
    private String apellido;
    private String observaciones;
    private User user;
    private Escribania escribania;
    private List<Archivos> archivo;
    private List<Tramite> tramite;
    private List<ArchivosDetalle> archivosDetalle;
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Escribania getEscribania() {
        return escribania;
    }

    public void setEscribania(Escribania escribania) {
        this.escribania = escribania;
    }

    public String getCuitEscribano() {
        return cuitEscribano;
    }

    public void setCuitEscribano(String cuitEscribano) {
        this.cuitEscribano = cuitEscribano;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Archivos> getArchivo() {
        return archivo;
    }

    public void setArchivo(List<Archivos> archivo) {
        this.archivo = archivo;
    }

    public List<Tramite> getTramite() {
        return tramite;
    }

    public void setTramite(List<Tramite> tramite) {
        this.tramite = tramite;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ArchivosDetalle> getArchivosDetalle() {
        return archivosDetalle;
    }

    public void setArchivosDetalle(List<ArchivosDetalle> archivosDetalle) {
        this.archivosDetalle = archivosDetalle;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
}

    

   

