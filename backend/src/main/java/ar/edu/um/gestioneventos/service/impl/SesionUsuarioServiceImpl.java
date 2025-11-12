package ar.edu.um.gestioneventos.service.impl;

import ar.edu.um.gestioneventos.domain.SesionUsuario;
import ar.edu.um.gestioneventos.repository.SesionUsuarioRepository;
import ar.edu.um.gestioneventos.repository.UserRepository;
import ar.edu.um.gestioneventos.service.SesionUsuarioService;
import ar.edu.um.gestioneventos.service.dto.SesionUsuarioDTO;
import ar.edu.um.gestioneventos.service.mapper.SesionUsuarioMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.edu.um.gestioneventos.domain.SesionUsuario}.
 */
@Service
@Transactional
public class SesionUsuarioServiceImpl implements SesionUsuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(SesionUsuarioServiceImpl.class);

    private final SesionUsuarioRepository sesionUsuarioRepository;

    private final SesionUsuarioMapper sesionUsuarioMapper;

    private final UserRepository userRepository;

    public SesionUsuarioServiceImpl(
        SesionUsuarioRepository sesionUsuarioRepository,
        SesionUsuarioMapper sesionUsuarioMapper,
        UserRepository userRepository
    ) {
        this.sesionUsuarioRepository = sesionUsuarioRepository;
        this.sesionUsuarioMapper = sesionUsuarioMapper;
        this.userRepository = userRepository;
    }

    @Override
    public SesionUsuarioDTO save(SesionUsuarioDTO sesionUsuarioDTO) {
        LOG.debug("Request to save SesionUsuario : {}", sesionUsuarioDTO);
        SesionUsuario sesionUsuario = sesionUsuarioMapper.toEntity(sesionUsuarioDTO);
        Long userId = sesionUsuario.getUser().getId();
        userRepository.findById(userId).ifPresent(sesionUsuario::user);
        sesionUsuario = sesionUsuarioRepository.save(sesionUsuario);
        return sesionUsuarioMapper.toDto(sesionUsuario);
    }

    @Override
    public SesionUsuarioDTO update(SesionUsuarioDTO sesionUsuarioDTO) {
        LOG.debug("Request to update SesionUsuario : {}", sesionUsuarioDTO);
        SesionUsuario sesionUsuario = sesionUsuarioMapper.toEntity(sesionUsuarioDTO);
        sesionUsuario = sesionUsuarioRepository.save(sesionUsuario);
        return sesionUsuarioMapper.toDto(sesionUsuario);
    }

    @Override
    public Optional<SesionUsuarioDTO> partialUpdate(SesionUsuarioDTO sesionUsuarioDTO) {
        LOG.debug("Request to partially update SesionUsuario : {}", sesionUsuarioDTO);

        return sesionUsuarioRepository
            .findById(sesionUsuarioDTO.getId())
            .map(existingSesionUsuario -> {
                sesionUsuarioMapper.partialUpdate(existingSesionUsuario, sesionUsuarioDTO);

                return existingSesionUsuario;
            })
            .map(sesionUsuarioRepository::save)
            .map(sesionUsuarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SesionUsuarioDTO> findAll() {
        LOG.debug("Request to get all SesionUsuarios");
        return sesionUsuarioRepository.findAll().stream().map(sesionUsuarioMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<SesionUsuarioDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sesionUsuarioRepository.findAllWithEagerRelationships(pageable).map(sesionUsuarioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SesionUsuarioDTO> findOne(Long id) {
        LOG.debug("Request to get SesionUsuario : {}", id);
        return sesionUsuarioRepository.findOneWithEagerRelationships(id).map(sesionUsuarioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete SesionUsuario : {}", id);
        sesionUsuarioRepository.deleteById(id);
    }
}
