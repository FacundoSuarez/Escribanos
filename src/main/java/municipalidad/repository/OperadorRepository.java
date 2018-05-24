package municipalidad.repository;

import municipalidad.domain.Operador;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Operador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long> {

}
