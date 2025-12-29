package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Usuario;
import com.mycompany.myapp.service.dto.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-27T18:52:17-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class UsuarioMapperImpl implements UsuarioMapper {

    @Override
    public Usuario toEntity(UsuarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setId( dto.getId() );
        usuario.setUsername( dto.getUsername() );
        usuario.setPassword( dto.getPassword() );
        usuario.setFirstName( dto.getFirstName() );
        usuario.setLastName( dto.getLastName() );
        usuario.setEmail( dto.getEmail() );
        usuario.setNombreAlumno( dto.getNombreAlumno() );
        usuario.setDescripcionProyecto( dto.getDescripcionProyecto() );
        usuario.setJwtToken( dto.getJwtToken() );
        usuario.setFechaRegistro( dto.getFechaRegistro() );

        return usuario;
    }

    @Override
    public UsuarioDTO toDto(Usuario entity) {
        if ( entity == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId( entity.getId() );
        usuarioDTO.setUsername( entity.getUsername() );
        usuarioDTO.setPassword( entity.getPassword() );
        usuarioDTO.setFirstName( entity.getFirstName() );
        usuarioDTO.setLastName( entity.getLastName() );
        usuarioDTO.setEmail( entity.getEmail() );
        usuarioDTO.setNombreAlumno( entity.getNombreAlumno() );
        usuarioDTO.setDescripcionProyecto( entity.getDescripcionProyecto() );
        usuarioDTO.setJwtToken( entity.getJwtToken() );
        usuarioDTO.setFechaRegistro( entity.getFechaRegistro() );

        return usuarioDTO;
    }

    @Override
    public List<Usuario> toEntity(List<UsuarioDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Usuario> list = new ArrayList<Usuario>( dtoList.size() );
        for ( UsuarioDTO usuarioDTO : dtoList ) {
            list.add( toEntity( usuarioDTO ) );
        }

        return list;
    }

    @Override
    public List<UsuarioDTO> toDto(List<Usuario> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UsuarioDTO> list = new ArrayList<UsuarioDTO>( entityList.size() );
        for ( Usuario usuario : entityList ) {
            list.add( toDto( usuario ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Usuario entity, UsuarioDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getUsername() != null ) {
            entity.setUsername( dto.getUsername() );
        }
        if ( dto.getPassword() != null ) {
            entity.setPassword( dto.getPassword() );
        }
        if ( dto.getFirstName() != null ) {
            entity.setFirstName( dto.getFirstName() );
        }
        if ( dto.getLastName() != null ) {
            entity.setLastName( dto.getLastName() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getNombreAlumno() != null ) {
            entity.setNombreAlumno( dto.getNombreAlumno() );
        }
        if ( dto.getDescripcionProyecto() != null ) {
            entity.setDescripcionProyecto( dto.getDescripcionProyecto() );
        }
        if ( dto.getJwtToken() != null ) {
            entity.setJwtToken( dto.getJwtToken() );
        }
        if ( dto.getFechaRegistro() != null ) {
            entity.setFechaRegistro( dto.getFechaRegistro() );
        }
    }
}
