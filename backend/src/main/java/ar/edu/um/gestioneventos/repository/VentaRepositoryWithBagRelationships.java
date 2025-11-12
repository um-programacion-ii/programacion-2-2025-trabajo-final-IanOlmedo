package ar.edu.um.gestioneventos.repository;

import ar.edu.um.gestioneventos.domain.Venta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface VentaRepositoryWithBagRelationships {
    Optional<Venta> fetchBagRelationships(Optional<Venta> venta);

    List<Venta> fetchBagRelationships(List<Venta> ventas);

    Page<Venta> fetchBagRelationships(Page<Venta> ventas);
}
