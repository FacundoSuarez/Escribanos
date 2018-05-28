package municipalidad.repository;

import java.util.List;
import java.util.Set;
import municipalidad.domain.Archivos;
import municipalidad.domain.Tramite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Archivos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArchivosRepository extends JpaRepository<Archivos, Long> {
    
    @Query("select a from Archivos a where a.tramite = :p_tramite")
    List<Archivos> findByTramite(@Param("p_tramite") Tramite p_tramite);

}
