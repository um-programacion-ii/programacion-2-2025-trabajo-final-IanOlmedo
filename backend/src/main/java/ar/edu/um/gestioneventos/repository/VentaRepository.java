package ar.edu.um.gestioneventos.repository;

import ar.edu.um.gestioneventos.domain.Venta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Venta entity.
 *
 * When extending this class, extend VentaRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface VentaRepository extends VentaRepositoryWithBagRelationships, JpaRepository<Venta, Long> {
    @Query("select venta from Venta venta where venta.usuario.login = ?#{authentication.name}")
    List<Venta> findByUsuarioIsCurrentUser();

    default Optional<Venta> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Venta> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Venta> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select venta from Venta venta left join fetch venta.evento left join fetch venta.usuario",
        countQuery = "select count(venta) from Venta venta"
    )
    Page<Venta> findAllWithToOneRelationships(Pageable pageable);

    @Query("select venta from Venta venta left join fetch venta.evento left join fetch venta.usuario")
    List<Venta> findAllWithToOneRelationships();

    @Query("select venta from Venta venta left join fetch venta.evento left join fetch venta.usuario where venta.id =:id")
    Optional<Venta> findOneWithToOneRelationships(@Param("id") Long id);
}
