package ar.edu.um.gestioneventos.service;

import ar.edu.um.gestioneventos.service.dto.AsientoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ar.edu.um.gestioneventos.domain.Asiento}.
 */
public interface AsientoService {
    /**
     * Save a asiento.
     *
     * @param asientoDTO the entity to save.
     * @return the persisted entity.
     */
    AsientoDTO save(AsientoDTO asientoDTO);

    /**
     * Updates a asiento.
     *
     * @param asientoDTO the entity to update.
     * @return the persisted entity.
     */
    AsientoDTO update(AsientoDTO asientoDTO);

    /**
     * Partially updates a asiento.
     *
     * @param asientoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AsientoDTO> partialUpdate(AsientoDTO asientoDTO);

    /**
     * Get all the asientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AsientoDTO> findAll(Pageable pageable);

    /**
     * Get all the asientos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AsientoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" asiento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AsientoDTO> findOne(Long id);

    /**
     * Delete the "id" asiento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
