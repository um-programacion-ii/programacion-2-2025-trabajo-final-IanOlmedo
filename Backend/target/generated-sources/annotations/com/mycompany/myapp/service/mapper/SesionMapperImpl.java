package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Sesion;
import com.mycompany.myapp.service.dto.SesionDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-27T18:52:19-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class SesionMapperImpl implements SesionMapper {

    @Override
    public Sesion toEntity(SesionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Sesion sesion = new Sesion();

        sesion.setId( dto.getId() );
        sesion.setToken( dto.getToken() );
        sesion.setEstadoFlujo( dto.getEstadoFlujo() );
        sesion.setEventoSeleccionado( dto.getEventoSeleccionado() );
        sesion.setAsientosSeleccionados( dto.getAsientosSeleccionados() );
        sesion.setUltimaActividad( dto.getUltimaActividad() );
        sesion.setExpiraEn( dto.getExpiraEn() );

        return sesion;
    }

    @Override
    public SesionDTO toDto(Sesion entity) {
        if ( entity == null ) {
            return null;
        }

        SesionDTO sesionDTO = new SesionDTO();

        sesionDTO.setId( entity.getId() );
        sesionDTO.setToken( entity.getToken() );
        sesionDTO.setEstadoFlujo( entity.getEstadoFlujo() );
        sesionDTO.setEventoSeleccionado( entity.getEventoSeleccionado() );
        sesionDTO.setAsientosSeleccionados( entity.getAsientosSeleccionados() );
        sesionDTO.setUltimaActividad( entity.getUltimaActividad() );
        sesionDTO.setExpiraEn( entity.getExpiraEn() );

        return sesionDTO;
    }

    @Override
    public List<Sesion> toEntity(List<SesionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Sesion> list = new ArrayList<Sesion>( dtoList.size() );
        for ( SesionDTO sesionDTO : dtoList ) {
            list.add( toEntity( sesionDTO ) );
        }

        return list;
    }

    @Override
    public List<SesionDTO> toDto(List<Sesion> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<SesionDTO> list = new ArrayList<SesionDTO>( entityList.size() );
        for ( Sesion sesion : entityList ) {
            list.add( toDto( sesion ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Sesion entity, SesionDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getToken() != null ) {
            entity.setToken( dto.getToken() );
        }
        if ( dto.getEstadoFlujo() != null ) {
            entity.setEstadoFlujo( dto.getEstadoFlujo() );
        }
        if ( dto.getEventoSeleccionado() != null ) {
            entity.setEventoSeleccionado( dto.getEventoSeleccionado() );
        }
        if ( dto.getAsientosSeleccionados() != null ) {
            entity.setAsientosSeleccionados( dto.getAsientosSeleccionados() );
        }
        if ( dto.getUltimaActividad() != null ) {
            entity.setUltimaActividad( dto.getUltimaActividad() );
        }
        if ( dto.getExpiraEn() != null ) {
            entity.setExpiraEn( dto.getExpiraEn() );
        }
    }
}
