package municipalidad.repository;

import java.util.List;
import municipalidad.domain.Escribania;
import municipalidad.domain.Presentacion;
import municipalidad.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the Presentacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PresentacionRepository extends JpaRepository<Presentacion, Long> {
    
    @Query("select p from Presentacion p where p.escribania = :p_escribania")
    List<Presentacion> findByEscribania(@Param("p_escribania")Escribania p_escribania);

    
}
