package municipalidad.repository;

import java.util.List;
import municipalidad.domain.ArchivosDetalle;
import municipalidad.domain.Tramite;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Tramite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {
    @Query("select a from Tramite a where a.archivosDetalle = :p_archivosDetalle")
    List<Tramite> findByArchivosDetalle(@Param("p_archivosDetalle") ArchivosDetalle p_archivosDetalle);

}
