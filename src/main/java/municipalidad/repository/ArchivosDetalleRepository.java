package municipalidad.repository;

import java.util.List;
import municipalidad.domain.ArchivosDetalle;
import municipalidad.domain.Presentacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the ArchivosDetalle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArchivosDetalleRepository extends JpaRepository<ArchivosDetalle, Long> {
    
    @Query("select a from ArchivosDetalle a where a.presentacion = :p_presentacion")
    List<ArchivosDetalle> findByPresentacion(@Param("p_presentacion") Presentacion p_presentacion);

}
